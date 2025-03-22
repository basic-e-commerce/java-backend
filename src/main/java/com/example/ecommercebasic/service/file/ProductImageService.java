package com.example.ecommercebasic.service.file;

import com.example.ecommercebasic.dto.file.FilePropertiesDto;
import com.example.ecommercebasic.dto.file.ProductImageRequestDto;
import com.example.ecommercebasic.entity.file.ImageType;
import com.example.ecommercebasic.entity.file.ProductImage;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.file.ProductImageRepository;
import com.example.ecommercebasic.service.storagestrategy.IStorageStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductImageService implements ImageService<ProductImage, ProductImageRequestDto> {
    private IStorageStrategy storageStrategy;
    private final ProductImageRepository productImageRepository;

    @Value("${upload.file.product.size}")
    private long productImageSize;

    @Value("${upload.file.dir}")
    private String uploadFileDir;  //   /var/www/upload/ecommerce/

    @Value("${upload.file.url}")
    private String uploadFileUrl;  //   http://localhost:8080/api/v1/upload/

    public ProductImageService(@Qualifier("localStorageStrategy") IStorageStrategy storageStrategy, ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
        this.storageStrategy = storageStrategy;
    }


    @Transactional
    @Override
    public ProductImage save(ProductImageRequestDto file, Long id) {
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

        String path = uploadFileDir+ ImageType.PRODUCT_IMAGE.getValue()+"/"+id+"/";
        FilePropertiesDto filePropertiesDto = getStorageStrategy().saveFile(file.getMultipartFile(), path);
        String newFilePath = path+filePropertiesDto.getName();
        String url = newFilePath.replace(uploadFileDir,uploadFileUrl);
        ProductImage productImage = new ProductImage(filePropertiesDto.getName(),
                filePropertiesDto.getSize(),
                filePropertiesDto.getResolution(),
                filePropertiesDto.getFormat(),
                url,
                file.getOrderIndex());
        return productImageRepository.save(productImage);
    }

    @Transactional
    @Override
    public String delete(Long id) {
        ProductImage productImage = getById(id);
        String path = productImage.getUrl().replace(uploadFileUrl,uploadFileDir);
        System.out.println("----------------------path : "+path);
        getStorageStrategy().deleteFile(path);

        return "deleted";
    }

    @Override
    public ProductImage getById(Long id) {
        return productImageRepository.findById(id).orElseThrow(()-> new NotFoundException("Product Image Not Found"));
    }

    @Override
    public List<ProductImage> getAll() {
        return productImageRepository.findAll();
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
}
