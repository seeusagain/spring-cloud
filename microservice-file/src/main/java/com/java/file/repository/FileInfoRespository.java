package com.java.file.repository;

import com.java.file.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRespository extends JpaRepository<FileInfo, String> {
    
}
