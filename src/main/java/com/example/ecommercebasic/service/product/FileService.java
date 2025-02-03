package com.example.ecommercebasic.service.product;

import com.example.ecommercebasic.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {
    @Value("${upload.dir}")
    private String uploadDir;  // /var/www/upload/
    @Value("${upload.url}")
    private String upluadUrl; // http://localhost:8080/
    @Value("${upload.file.url}")
    private String uploadFileUrl;   // http://localhost:8080/api/upload/

    public FileService() {
    }

    public String uploadImage(MultipartFile file, String paths) {
        String newPath = uploadDir + paths;
        System.out.println("newPath: "+ newPath);
        Path path = Paths.get(newPath);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }

            String fileExtension = "";
            int lastIndexOfDot = originalFileName.lastIndexOf(".");
            if (lastIndexOfDot != -1) {
                fileExtension = originalFileName.substring(lastIndexOfDot); // Örneğin ".jpg"
            }

            String newFileName = UUID.randomUUID() + fileExtension;

            Path filePath = path.resolve(newFileName);

            // Aynı isimde bir dosya var mı kontrol et
            if (Files.exists(filePath)) {
                throw new RuntimeException("A file with the name '" + originalFileName + "' already exists.");
            }

            String urls = uploadFileUrl+paths+newFileName;
            System.out.println("urls: "+ urls);

            file.transferTo(filePath.toFile());
            return urls;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Transactional
    public String deleteImage(String url) {

        // http://localhost:8080/api/upload/images/6e07c4f8-a3c6-4c65-92f3-84190b2e1f76.jpg
        String path = url.replace(uploadFileUrl,uploadDir);
        System.out.println("path: "+ path);

        File file = new File(path);

        if (!file.exists()) {
            throw new NotFoundException("File not found: " + path);
        }

        if (file.delete()) { // Dosya silinir.
            return "deleted";
        } else {
            throw new RuntimeException("Failed to delete file: ");// + image.getUrl());
        }

    }
}
