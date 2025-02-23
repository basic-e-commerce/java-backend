package com.example.ecommercebasic.service.product.attribute;

import com.example.ecommercebasic.entity.product.attribute.Attribute;
import com.example.ecommercebasic.entity.product.attribute.AttributeValue;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.product.attribute.AttributeValueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeValueService {
    private final AttributeValueRepository attributeValueRepository;

    public AttributeValueService(AttributeValueRepository attributeValueRepository) {
        this.attributeValueRepository = attributeValueRepository;
    }

    public AttributeValue createAttributeValue(String attributeName) {
        AttributeValue attributeValue = new AttributeValue(attributeName);
        return attributeValueRepository.save(attributeValue);
    }

    public AttributeValue createAttributeValue(String attributeName, Attribute attribute) {
        AttributeValue attributeValue = new AttributeValue(attributeName);
        attributeValue.setAttribute(attribute);
        return attributeValueRepository.save(attributeValue);
    }

    public AttributeValue findById(Integer attributeValueId) {
        return attributeValueRepository.findById(attributeValueId).orElseThrow(()-> new NotFoundException("Attribute Value Not Found"));
    }

    public List<AttributeValue> findAll() {
        return attributeValueRepository.findAll();
    }
}
