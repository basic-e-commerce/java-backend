package com.example.ecommercebasic.entity.file;

import jakarta.persistence.Entity;

@Entity
public class Document extends File {
    private String fileType; // Örn: PDF, DOCX
    private int pageCount; // Sayfa sayısı

    public Document(String name, Long size, String fileType, int pageCount) {
        super(name, size);
        this.fileType = fileType;
        this.pageCount = pageCount;
    }

    public Document() {
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
