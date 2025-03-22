package com.example.ecommercebasic.entity.file;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Image extends File{
    private String resolution; // Örn: 1920x1080
    private String format; // Örn: JPEG, PNG

    public Image(String name, Long size, String resolution, String format) {
        super(name, size);
        this.resolution = resolution;
        this.format = format;
    }

    public Image() {
        super();
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
