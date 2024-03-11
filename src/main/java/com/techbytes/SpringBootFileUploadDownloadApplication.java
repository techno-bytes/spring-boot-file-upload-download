package com.techbytes;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;

@SpringBootApplication
public class SpringBootFileUploadDownloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFileUploadDownloadApplication.class, args);
	}

}
