package com.example.ecommercebasic.dto.file;

import org.springframework.web.multipart.MultipartFile;

public class ProductImageRequestDto extends ImageRequestDto{
    private int orderIndex;
    public ProductImageRequestDto(MultipartFile multipartFile, int orderIndex) {
        super(multipartFile);
        this.orderIndex = orderIndex;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
}
