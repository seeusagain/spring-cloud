package com.java.file.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.java.dto.ResultMsg;
import com.java.file.entity.FileInfo;
import com.java.file.repository.FileInfoRespository;
import com.java.file.service.IFileService;
import com.java.file.utils.FTPUtil;
import com.java.utils.EmptyUtils;
import com.java.utils.IdGeneratorUtils;
import com.jcraft.jsch.ChannelSftp;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lu.xu on 2018/1/8. TODO: 文件操作实现类
 */
@Service
public class FileServiceImpl implements IFileService {

  private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

  @Resource
  private FileInfoRespository fileInfoRespository;

  @Value("${file.host}")
  private String remoteHost;

  @Value("${file.port}")
  private int port;

  @Value("${file.rootDirectory}")
  private String rootDirectory;

  @Value("${file.user}")
  private String userName;

  @Value("${file.password}")
  private String password;

  @Value("${user.token.keep-time}")
  private int userTokenKeepTime;

  @Resource
  private StringRedisTemplate stringRedisTemplate;

  @Override
  public ResultMsg uploadFile(MultipartFile file, String accessToken) throws Exception {
    if (EmptyUtils.isEmpty(accessToken)) {
      return ResultMsg.error("accessToken无效");
    }
    String userId = stringRedisTemplate.opsForValue().get(accessToken.toString());
    if (EmptyUtils.isNotEmpty(userId)) {
      //更新redis有效时间
      stringRedisTemplate.opsForValue()
          .set(accessToken.toString(), userId, userTokenKeepTime, TimeUnit.SECONDS);
    } else {
      return ResultMsg.error("无权访问");
    }
    if (file.isEmpty()) {
      return ResultMsg.error("文件为空");
    }
    /**
     * 文件名称乱码的话，see：
     * http://blog.csdn.net/forezp/article/details/77170470
     * https://github.com/spring-cloud/spring-cloud-netflix/issues/1385
     */
    String fileName = file.getOriginalFilename();
    logger.info(">>文件上传：文件名：{}", fileName);

    int index = fileName.lastIndexOf(".");
    if (index == -1) {
      return ResultMsg.error("文件必须存在后缀名");
    }

    String fileId = IdGeneratorUtils.getSerialNo();
    String realName = fileName.substring(0, index);
    String suffix = fileName.substring(index, fileName.length());

    //按照年、月、日 生成文件夹
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String ymd = sdf.format(new Date());
    String[] s = ymd.split("-");
    final String finalFileDir = new StringBuffer().append(rootDirectory)
        .append(s[0])
        .append("/")
        //            .append(File.separator)
        .append(s[1])
        .append("/")
        //            .append(File.separator)
        .append(s[2])
        .append("/")
        //            .append(File.separator)
        .toString();

    //上传
    boolean flag = this.doUploadFile(file.getInputStream(), finalFileDir, fileId + suffix);
    if (!flag) {
      return ResultMsg.error("上传失败");
    }
    //元数据保存数据库
    FileInfo fileInfo = new FileInfo();
    fileInfo.setId(fileId);
    fileInfo.setDirectory(finalFileDir);
    fileInfo.setFileName(realName);
    fileInfo.setFileSuffix(suffix);
    fileInfo.setCreateTime(new Date());
    this.fileInfoRespository.save(fileInfo);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("fileId", fileId);
    return ResultMsg.ok("上传成功", jsonObject);
  }

  /**
   * SFTP上传文件
   */
  private boolean doUploadFile(InputStream fileStream, String directory, String fileName) {
    ChannelSftp sftp = null;
    try {
      //开启sftp通道
      sftp = this.getChannelSftp();
      //创建文件夹
      FTPUtil.createDir(directory, sftp);
      //上传
      sftp.cd(directory);
      sftp.put(fileStream, fileName);
      logger.info("文件:{} 上传成功！ 存放目录{}", fileName, directory);
      return true;
    } catch (Exception e) {
      logger.error("文件上传出错");
      logger.error(e.getMessage());
      e.printStackTrace();
      return false;
    } finally {
      FTPUtil.closeChannel(sftp);
    }
  }

  public ChannelSftp getChannelSftp() throws Exception {
    return FTPUtil.ChannelSftp(remoteHost, Integer.valueOf(port), userName, password);
  }

  @Override
  public ResultMsg removeFile(String fileId, String accessToken) {
    if (EmptyUtils.isEmpty(accessToken)) {
      return ResultMsg.error("accessToken无效");
    }
    String userId = stringRedisTemplate.opsForValue().get(accessToken.toString());
    if (EmptyUtils.isEmpty(userId)) {
      return ResultMsg.error("无权访问");
    }
    logger.info(">>删除文件，删除人：{}；文件id：{}", userId, fileId);

    FileInfo fileInfo = this.getFileInfo(fileId);
    if (fileInfo == null) {
      return ResultMsg.error("未查询到文件信息");
    }
    logger.info("fileInfo:" + fileInfo.getDirectory() + " " + fileInfo.getFileName() + fileInfo
        .getFileSuffix());
    ChannelSftp sftp = null;
    try {
      //开启sftp通道
      sftp = this.getChannelSftp();
      sftp.cd(fileInfo.getDirectory());
      sftp.rm(fileInfo.getId() + fileInfo.getFileSuffix());
      logger.info(fileInfo.getId() + fileInfo.getFileSuffix() + "已被删除");
      this.fileInfoRespository.delete(fileId);
      return ResultMsg.ok("删除成功");
    } catch (Exception e) {
      logger.error("删除文件异常");
      logger.error(e.getMessage());
      e.printStackTrace();
      return ResultMsg.error("删除文件异常");
    } finally {
      FTPUtil.closeChannel(sftp);
    }
  }

  @Override
  public FileInfo getFileInfo(String fileId) {
    return fileId == null ? null : this.fileInfoRespository.findOne(fileId);
  }

}
