package com.example.ecommercebasic.builder.product;


import com.example.ecommercebasic.dto.product.CategorySmallDto;
import com.example.ecommercebasic.entity.product.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryBuilder {

    public CategorySmallDto buildCategory(Category category) {

        List<CategorySmallDto> subCategoryDtos = category.getSubCategories().stream()
                .map(this::buildCategory)
                .collect(Collectors.toList());

        String url="";
        if (category.getCoverUrl()!=null)
            url = category.getCoverUrl();

        return new CategorySmallDto(
                category.getId(),
                category.getName(),
                url,
                category.isSubCategory(),
                subCategoryDtos,
                category.getAttributes()
        );
    }

}
