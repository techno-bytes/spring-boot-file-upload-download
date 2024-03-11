package com.techbytes.service;

import com.techbytes.entity.Attachment;
import com.techbytes.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AttachmentServiceImpl  implements AttachmentService{

    private AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment saveFile(MultipartFile file) throws IOException {
        Attachment attachment = buildAttachment(file);
        return attachmentRepository.save(attachment);
    }

    @Override
    public List<Attachment> saveFiles(MultipartFile[] files) {
        List<Attachment> attachments = Stream.of(files).map(file -> buildAttachment(file))
                .collect(Collectors.toList());
        return attachmentRepository.saveAll(attachments);
    }

    public Attachment findById(String fileId) throws Exception {
       return attachmentRepository.findById(fileId).orElseThrow(()
                -> new Exception("File not found with id: " + fileId));
    }

    @Override
    public List<Attachment> findAllFiles() {
        return attachmentRepository.findAll();
    }

    private static Attachment buildAttachment(MultipartFile file) {
        Attachment attachment = new Attachment();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        try {
            attachment.setData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return attachment;
    }
}
