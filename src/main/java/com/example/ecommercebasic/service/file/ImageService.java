package com.example.ecommercebasic.service.file;

import com.example.ecommercebasic.dto.file.FileRequestDto;
import com.example.ecommercebasic.entity.file.File;
import com.example.ecommercebasic.entity.file.Image;
import com.example.ecommercebasic.service.storagestrategy.IStorageStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ImageService<T extends Image,R extends FileRequestDto> extends IFileService<T,R> {
    boolean isValidExtension(String extension);
}
