package com.example.ecommercebasic.dto.product.attribute;

import java.util.List;

public class AttributeRequestDto {
    private String attributeName;
    private List<String> attributeValues;

    public AttributeRequestDto(String attributeName, List<String> attributeValues) {
        this.attributeName = attributeName;
        this.attributeValues = attributeValues;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<String> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<String> attributeValues) {
        this.attributeValues = attributeValues;
    }
}
