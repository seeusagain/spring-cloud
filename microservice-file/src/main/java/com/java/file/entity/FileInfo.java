package com.java.file.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "basic_file_info")
public class FileInfo {
    @Id
    private String id;
    
    private String directory;
    
    private String fileName;
    
    private String fileSuffix;
    
    private Date createTime;
    
    private String createUser;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getDirectory() {
        return directory;
    }
    
    public void setDirectory(String directory) {
        this.directory = directory == null ? null : directory.trim();
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
    
    public String getFileSuffix() {
        return fileSuffix;
    }
    
    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }
}