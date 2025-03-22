package com.example.ecommercebasic.service.storagestrategy;

import com.example.ecommercebasic.dto.file.FilePropertiesDto;
import jakarta.transaction.Transactional;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class LocalStorageStrategy implements IStorageStrategy{

    @Transactional
    @Override
    public FilePropertiesDto saveFile(MultipartFile file, String directoryPath) {
        // directoryPath tam olarak konumu göstermelidir.
        // /var/www/upload/ecommerce/coverimage/{product_id}/   olması gereken budur
        Path path = Paths.get(directoryPath);

        try {
            // Create directories if they do not exist
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Directory created: " + directoryPath);
            }

            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new RuntimeException("File name is invalid");
            }
            String fileExtension = getFileExtension(originalFileName);
            String safeFileName = sanitizeFileName(originalFileName);

            // Yeni dosya adı oluştur
            Path filePath = path.resolve(safeFileName);
            System.out.println("filePath-------------: " + filePath);

            // Aynı isimde dosya var mı kontrol et
            if (Files.exists(filePath)) {
                throw new RuntimeException("A file with the name '" + originalFileName + "' already exists.");
            }

            // Dosyayı kaydet
            file.transferTo(filePath.toFile());

            // Dosya bilgilerini al
            long fileSize = file.getSize();
            String format = detectFileType(file); // Dosya türünü belirle
            String resolution = format.startsWith("image/") ? getImageResolution(filePath) : "0";


            return new FilePropertiesDto(safeFileName , fileSize, resolution, fileExtension);
        }catch (IOException e) {
            throw new RuntimeException("Error uploading file: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("File name is invalid");
        }
        if (!exists(filePath)) {
            throw new RuntimeException("File not found: " + filePath);
        }
        File file = new File(filePath);
        if (file.delete()) {
            System.out.println("File deleted: " + filePath);
        }else
            throw new RuntimeException("Unable to delete file: " + filePath);

    }

    @Override
    public boolean exists(String filePath) {
        File file = new File(filePath);
        if (file.exists())
            return true;
        else
            return false;
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return ""; // Uzantı yoksa veya sadece nokta varsa boş string döndür
        }
        return fileName.substring(lastDotIndex + 1); // Noktasız döndür
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }

    // MIME türünü belirleme (Tika kullanarak daha güvenilir hale getirildi)
    private String detectFileType(MultipartFile file) {
        Tika tika = new Tika();
        try {
            return tika.detect(file.getInputStream());
        } catch (IOException e) {
            return "unknown";
        }
    }

    // Görsel çözünürlüğünü almak için bir metot ekle (sadece görseller için)
    private String getImageResolution(Path filePath) {
        try {
            BufferedImage image = ImageIO.read(filePath.toFile());
            if (image != null) {
                return image.getWidth() + "x" + image.getHeight();
            }
        } catch (IOException e) {
            System.out.println("Could not determine image resolution: " + e.getMessage());
        }
        return "Unknown";
    }
}
