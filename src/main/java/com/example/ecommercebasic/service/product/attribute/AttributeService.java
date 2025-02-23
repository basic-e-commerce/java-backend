package com.example.ecommercebasic.service.product.attribute;

import com.example.ecommercebasic.dto.product.attribute.AttributeRequestDto;
import com.example.ecommercebasic.entity.product.Category;
import com.example.ecommercebasic.entity.product.attribute.Attribute;
import com.example.ecommercebasic.entity.product.attribute.AttributeType;
import com.example.ecommercebasic.entity.product.attribute.AttributeValue;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.product.ProductRepository;
import com.example.ecommercebasic.repository.product.attribute.AttributeRepository;
import com.example.ecommercebasic.repository.product.attribute.AttributeValueRepository;
import com.example.ecommercebasic.service.product.CategoryService;
import com.example.ecommercebasic.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;

import java.util.List;

@Service
public class AttributeService {
    private final AttributeRepository attributeRepository;
    private final AttributeValueService attributeValueService;


    public AttributeService(AttributeRepository attributeRepository, AttributeValueService attributeValueService) {
        this.attributeRepository = attributeRepository;
        this.attributeValueService = attributeValueService;
    }

    public String createAttribute(AttributeRequestDto attributeRequestDto) {
        Attribute attribute = new Attribute(attributeRequestDto.getAttributeName(), AttributeType.SELECT);
        Attribute saveAttribute = attributeRepository.save(attribute);

        for (String name : attributeRequestDto.getAttributeValues()) {
            AttributeValue attributeValue = attributeValueService.createAttributeValue(name, saveAttribute);
            saveAttribute.getAttributeValues().add(attributeValue);
        }
        return "success";
    }

    public List<Attribute> findAll() {
        return attributeRepository.findAll();
    }

    public Attribute findById(int attributeId) {
        return attributeRepository.findById(attributeId).orElseThrow(()-> new NotFoundException("Attribute not found"));
    }

    public String deleteById(int attributeId) {
        Attribute attribute = findById(attributeId);
        attributeRepository.delete(attribute);

        return "success";
    }

    public List<AttributeValue> findAllAttributeValue() {
        return attributeValueService.findAll();
    }
}
