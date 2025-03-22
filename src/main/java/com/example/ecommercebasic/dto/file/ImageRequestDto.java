package com.example.ecommercebasic.dto.file;

import org.springframework.web.multipart.MultipartFile;

public class ImageRequestDto extends FileRequestDto {
    public ImageRequestDto(MultipartFile multipartFile) {
        super(multipartFile);
    }
}
