package com.java.file.utils;

import java.io.File;
import java.util.Properties;

import com.java.file.service.impl.FileServiceImpl;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP文件操作类
 */
public class FTPUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    
    /**
     * 创建ChannelSftp
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public static ChannelSftp ChannelSftp(String host, int port, String username, String password) throws Exception {
        JSch jsch = new JSch();
        jsch.getSession(username, host, port);
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;
        logger.info("sftp connect to {}", host);
        return sftp;
    }
    
    public static void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
        logger.info("channel closed");
    }
    
    public static void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
    
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.getName().equals(fileName)) {
                file.delete();
            }
        }
    }
    
    /**
     * 创建文件夹
     * @param createpath
     * @param sftp
     * @throws Exception
     */
    public static void createDir(String createpath, ChannelSftp sftp) throws Exception {
        if (isDirExist(createpath, sftp)) {
            sftp.cd(createpath);
        }
        String pathArrry[] = createpath.split("/");
        StringBuffer filePath = new StringBuffer("/");
        for (String path : pathArrry) {
            if (path.equals("")) {
                continue;
            }
            filePath.append(path + "/");
            if (isDirExist(filePath.toString(), sftp)) {
                sftp.cd(filePath.toString());
            } else {
                sftp.mkdir(filePath.toString());
                sftp.cd(filePath.toString());
            }
        }
        
    }
    
    /** 
     * 判断目录是否存在 
     */
    public static boolean isDirExist(String directory, ChannelSftp sftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }
}
