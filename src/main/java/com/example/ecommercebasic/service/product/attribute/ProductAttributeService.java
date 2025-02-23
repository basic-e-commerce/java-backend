package com.example.ecommercebasic.service.product.attribute;

import com.example.ecommercebasic.dto.product.attribute.ProductAttributeRequestDto;
import com.example.ecommercebasic.entity.product.Product;
import com.example.ecommercebasic.entity.product.attribute.Attribute;
import com.example.ecommercebasic.entity.product.attribute.AttributeValue;
import com.example.ecommercebasic.entity.product.attribute.ProductAttribute;
import com.example.ecommercebasic.exception.BadRequestException;
import com.example.ecommercebasic.exception.ResourceAlreadyExistException;
import com.example.ecommercebasic.repository.product.attribute.ProductAttributeRepository;
import com.example.ecommercebasic.service.product.CategoryService;
import com.example.ecommercebasic.service.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductAttributeService {
    private final ProductAttributeRepository productAttributeRepository;
    private final AttributeService attributeService;
    private final AttributeValueService attributeValueService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductAttributeService(ProductAttributeRepository productAttributeRepository, AttributeService attributeService , AttributeValueService attributeValueService, ProductService productService, CategoryService categoryService) {
        this.productAttributeRepository = productAttributeRepository;
        this.attributeService = attributeService;
        this.attributeValueService = attributeValueService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    public ProductAttribute save(ProductAttributeRequestDto productAttributeRequestDto) {
        Product product = productService.findById(productAttributeRequestDto.getProductId());
        Attribute attribute = attributeService.findById(productAttributeRequestDto.getAttributeId());

        for (ProductAttribute productAttributes: product.getAttributes()) {
            if (attribute.getId() == productAttributes.getAttribute().getId())
                return addProductAttributeValue(productAttributeRequestDto.getAttributeValueIds(),productAttributes);
        }
        HashSet<AttributeValue> attributeValues = new HashSet<>();
        for (Integer attributeValueId: productAttributeRequestDto.getAttributeValueIds()){
            AttributeValue attributeValue = attributeValueService.findById(attributeValueId);
            if (attribute.getAttributeValues().contains(attributeValue)){
                try {
                    attributeValues.add(attributeValue);
                }catch (Exception e){
                    throw new ResourceAlreadyExistException("Attribute value already exists");
                }
            }else
                throw new BadRequestException("Invalid Attribute Value");
        }
        ProductAttribute productAttributeEntity = new ProductAttribute(product,attribute,attributeValues);
        ProductAttribute save = productAttributeRepository.save(productAttributeEntity);
        product.getCategories().forEach(category -> {
            category.getAttributes().add(attribute);
            categoryService.save(category);
        });
        return save;
    }


    private ProductAttribute addProductAttributeValue(List<Integer> attributeValueIds,ProductAttribute productAttribute) {
        Set<AttributeValue> attributeValues = productAttribute.getAttributeValue();
        Set<AttributeValue> newAttributeValues = new HashSet<>();

        for (Integer attributeValueId: attributeValueIds){
            AttributeValue attributeValue = attributeValueService.findById(attributeValueId);
            if (!attributeValues.contains(attributeValue)){
                System.out.println(attributeValue.getValue());
                newAttributeValues.add(attributeValue);
            }
        }
        productAttribute.getAttributeValue().addAll(newAttributeValues);
        return productAttributeRepository.save(productAttribute);
    }

}
