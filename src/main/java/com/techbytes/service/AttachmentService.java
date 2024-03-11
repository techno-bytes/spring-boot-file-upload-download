package com.techbytes.service;

import com.techbytes.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttachmentService {
    public Attachment saveFile(MultipartFile file) throws IOException;
    public List<Attachment> saveFiles(MultipartFile[] files) throws IOException;
    public Attachment findById(String fileId) throws Exception;

    List<Attachment> findAllFiles();
}
