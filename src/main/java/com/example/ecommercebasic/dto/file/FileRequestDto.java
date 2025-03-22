package com.example.ecommercebasic.dto.file;

import org.springframework.web.multipart.MultipartFile;

public class FileRequestDto {
    private MultipartFile multipartFile;

    public FileRequestDto(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
