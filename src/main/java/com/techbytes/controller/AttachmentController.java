package com.techbytes.controller;

import com.techbytes.entity.Attachment;
import com.techbytes.model.ResponseData;
import com.techbytes.service.AttachmentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class AttachmentController {

    private AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public List<ResponseData> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("File size= "+file.getSize());
        Attachment attachment = attachmentService.saveFile(file);
        return buildDownloadURL(List.of(attachment));
    }

    @PostMapping("/upload-multiple")
    public List<ResponseData> uploadMultipleFile(@RequestParam("file") MultipartFile[] files) throws IOException {
        System.out.println("No of files= "+files.length);
        List<Attachment> attachments = attachmentService.saveFiles(files);
        return buildDownloadURL(attachments);
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId) throws Exception {
        Attachment attachment = attachmentService.findById(fileId);
        return downloadFile(attachment);
    }

    @GetMapping("/download-all")
    public List<ResponseData> downloadFile() throws Exception {
        List<Attachment> attachments = attachmentService.findAllFiles();
        return buildDownloadURL(attachments);
    }

    private  List<ResponseData> buildDownloadURL(List<Attachment> attachments) {
        List<ResponseData> filesResponse = new ArrayList<>();
        attachments.stream().filter(attachment1 -> attachment1.getFileName()!=null)
                .forEach(attachment -> {
            String downloadPath = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/" + attachment.getId()).build().toString();
            filesResponse.add(new ResponseData(attachment.getFileName(),
                    downloadPath, attachment.getFileType(), attachment.getData().length));
        });
        return filesResponse;
    }

    private ResponseEntity<ByteArrayResource> downloadFile(Attachment attachment){
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\""+
                        attachment.getFileName()+"\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
