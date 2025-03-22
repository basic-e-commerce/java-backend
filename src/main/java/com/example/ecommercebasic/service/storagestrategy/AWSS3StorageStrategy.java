package com.example.ecommercebasic.service.storagestrategy;

import com.example.ecommercebasic.dto.file.FilePropertiesDto;
import org.springframework.web.multipart.MultipartFile;

public class AWSS3StorageStrategy implements IStorageStrategy {

    @Override
    public FilePropertiesDto saveFile(MultipartFile file, String fileName) {
        return null;
    }

    @Override
    public void deleteFile(String filePath) {

    }

    @Override
    public boolean exists(String filePath) {
        return false;
    }
}
