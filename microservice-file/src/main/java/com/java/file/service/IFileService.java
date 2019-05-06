package com.java.file.service;

import com.java.dto.ResultMsg;
import com.java.file.entity.FileInfo;
import com.jcraft.jsch.ChannelSftp;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lu.xu on 2018/1/8. TODO:文件操作接口
 */
public interface IFileService {

  ResultMsg uploadFile(MultipartFile file, String accessToken) throws Exception;

  ResultMsg removeFile(String fileId, String accessToken);

  FileInfo getFileInfo(String fileId);

  ChannelSftp getChannelSftp() throws Exception;
}
