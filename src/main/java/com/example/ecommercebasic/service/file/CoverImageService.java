package com.example.ecommercebasic.service.file;

import com.example.ecommercebasic.dto.file.FilePropertiesDto;
import com.example.ecommercebasic.dto.file.ImageRequestDto;
import com.example.ecommercebasic.entity.file.CoverImage;
import com.example.ecommercebasic.entity.file.File;
import com.example.ecommercebasic.entity.file.Image;
import com.example.ecommercebasic.entity.file.ImageType;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.repository.file.CoverImageRepository;
import com.example.ecommercebasic.service.storagestrategy.IStorageStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CoverImageService implements ImageService<CoverImage, ImageRequestDto> {
    @Value("${upload.file.dir}")
    private String uploadFileDir;  //   /var/www/upload/ecommerce/

    @Value("${upload.file.url}")
    private String uploadFileUrl;  //   http://localhost:8080/api/v1/upload/
    @Value("${upload.file.product.size}")
    private long productImageSize;


    private IStorageStrategy storageStrategy;
    private final CoverImageRepository coverImageRepository;

    public CoverImageService(@Qualifier("localStorageStrategy") IStorageStrategy storageStrategy, CoverImageRepository coverImageRepository) {
        this.coverImageRepository = coverImageRepository;
        this.storageStrategy = storageStrategy;
    }



    @Transactional
    @Override
    public CoverImage save(ImageRequestDto file,Long id) {

        long maxSize = productImageSize;
        if (file.getMultipartFile().getSize() > maxSize) {
            throw new RuntimeException("File size exceeds the maximum limit of 5MB.");
        }

        if (file.getMultipartFile().isEmpty()) {
            throw new RuntimeException("File is empty. Please select a valid file.");
        }

        String originalFileName = file.getMultipartFile().getOriginalFilename();
        // Dosya uzantısını kontrol et
        String fileExtension = getFileExtension(originalFileName);
        if (fileExtension == null || !isValidExtension(fileExtension)) {
            throw new RuntimeException("Invalid file type. Allowed extensions: .jpg, .png, .gif");
        }

        String path = uploadFileDir+ImageType.PRODUCT_COVER_IMAGE.getValue()+"/"+id+"/";

        FilePropertiesDto filePropertiesDto = getStorageStrategy().saveFile(file.getMultipartFile(), path);

        String newFilePath = path+filePropertiesDto.getName();
        String url = newFilePath.replace(uploadFileDir,uploadFileUrl);

        CoverImage coverImage = new CoverImage(filePropertiesDto.getName(), filePropertiesDto.getSize(), filePropertiesDto.getResolution(),filePropertiesDto.getFormat(),url);
        return coverImageRepository.save(coverImage);
    }

    @Transactional
    @Override
    public String delete(Long id) {
        CoverImage coverImage = getById(id);
        String path = coverImage.getUrl().replace(uploadFileUrl,uploadFileDir);
        System.out.println("pppath: "+path);
        getStorageStrategy().deleteFile(path);

        return "deleted";
    }

    @Override
    public CoverImage getById(Long id) {
        return coverImageRepository.findById(id).orElseThrow(() -> new BadRequestException("CoverImage not found."));
    }

    @Override
    public List<CoverImage> getAll() {
        return coverImageRepository.findAll();
    }


    @Override
    public void setStorageStrategy(IStorageStrategy strategy) {
        this.storageStrategy = strategy;
    }

    @Override
    public String getFileExtension(String originalFileName) {
        int lastIndexOfDot = originalFileName.lastIndexOf(".");
        if (lastIndexOfDot != -1) {
            return originalFileName.substring(lastIndexOfDot); // Örneğin ".jpg"
        }else
            throw new BadRequestException("Invalid file name.");
    }

    public IStorageStrategy getStorageStrategy() {
        return storageStrategy;
    }


    // Dosya uzantısını geçerli olup olmadığını kontrol et
    @Override
    public boolean isValidExtension(String extension) {
        Set<String> validExtensions = Set.of(".jpg", ".png", ".jpeg");
        return validExtensions.contains(extension.toLowerCase());
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9_.-]", "_");
    }
}
