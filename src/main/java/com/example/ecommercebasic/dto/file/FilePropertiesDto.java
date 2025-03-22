package com.example.ecommercebasic.dto.file;

public class FilePropertiesDto {
    private String name;
    private Long size; // Dosya boyutu (byte cinsinden)
    private String resolution; // Örn: 1920x1080
    private String format; // Örn: JPEG, PNG

    public FilePropertiesDto(String name, Long size, String resolution, String format) {
        this.name = name;
        this.size = size;
        this.resolution = resolution;
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
