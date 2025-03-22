package com.example.ecommercebasic.service.storagestrategy;

import com.example.ecommercebasic.dto.file.FilePropertiesDto;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageStrategy {
    FilePropertiesDto saveFile(MultipartFile file, String fileName); // Dosyayı kaydet
    void deleteFile(String filePath); // Dosyayı sil
    boolean exists(String filePath);

}
