package com.java.file.ctrl;

import com.java.file.entity.FileInfo;
import com.java.file.service.IFileService;
import com.java.file.utils.FTPUtil;
import com.java.utils.EmptyUtils;
import com.jcraft.jsch.ChannelSftp;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;

/**
 * 文件服务器
 */
@Api(description = "文件操作接口")
@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    
    @Resource
    private IFileService fileService;
    
    @ApiOperation(value = "文件上传", notes = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "文件"),
        @ApiImplicitParam(name = "accessToken", value = "令牌", required = true, paramType = "query")})
    @PostMapping(value = "/fileUpload")
    public Object fileUpload(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "accessToken", required = false) String accessToken) throws Exception {
        logger.info(">> file upload :" + request.getCharacterEncoding());
        return this.fileService.uploadFile(file, accessToken);
    }
    
    @ApiOperation(value = "文件下载", notes = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "fileId", value = "文件ID", paramType = "query"),
        @ApiImplicitParam(name = "accessToken", value = "令牌", required = true, paramType = "query")})
    @GetMapping(value = "/dowLoadFile")
    public void dowLoadFile(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "fileId", required = false) String fileId,
        @RequestParam(value = "accessToken", required = false) String accessToken) throws Exception {
        
        FileInfo fileInfo = this.fileService.getFileInfo(fileId);
        if (null == fileInfo) {
            logger.info("无法查询到文件信息");
            return;
        }
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ChannelSftp sftp = null;
        
        try {
            //获取文件，不写在service层是因为需要在现在完毕之后才能关闭channel
            sftp = this.fileService.getChannelSftp();
            sftp.cd(fileInfo.getDirectory());
            InputStream inputStream = sftp.get(fileInfo.getId() + fileInfo.getFileSuffix());
            if (null == inputStream) {
                logger.info("文件流为空");
                return;
            }
            //下载
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename="
                + new String((fileInfo.getFileName() + fileInfo.getFileSuffix()).getBytes("utf-8"), "ISO8859-1"));
            bis = new BufferedInputStream(inputStream);
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
            FTPUtil.closeChannel(sftp);
        }
    }
    
    @ApiOperation(value = "删除文件", notes = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "fileId", value = "文件ID", paramType = "query"),
        @ApiImplicitParam(name = "accessToken", value = "令牌", required = true, paramType = "query")})
    @PostMapping(value = "/removeFile")
    public Object removeFile(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "fileId", required = false) String fileId,
        @RequestParam(value = "accessToken", required = false) String accessToken) {
        return this.fileService.removeFile(fileId, accessToken);
    }
}
