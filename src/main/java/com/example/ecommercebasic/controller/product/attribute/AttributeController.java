package com.example.ecommercebasic.controller.product.attribute;

import com.example.ecommercebasic.dto.product.attribute.AttributeRequestDto;
import com.example.ecommercebasic.entity.product.attribute.Attribute;
import com.example.ecommercebasic.entity.product.attribute.AttributeValue;
import com.example.ecommercebasic.service.product.attribute.AttributeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Attr;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attribute")
public class AttributeController {
    private final AttributeService attributeService;

    public AttributeController(AttributeService attributeService) {
        this.attributeService = attributeService;
    }

    @PostMapping
    public ResponseEntity<String> createAttribute(@RequestBody AttributeRequestDto attributeRequestDto) {
        return new ResponseEntity<>(attributeService.createAttribute(attributeRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Attribute>> getAllAttribute(){
        return new ResponseEntity<>(attributeService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/attribute-value")
    public ResponseEntity<List<AttributeValue>> getAllAttributeValue(){
        return new ResponseEntity<>(attributeService.findAllAttributeValue(),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAttribute(@RequestParam int attributeId) {
        return new ResponseEntity<>(attributeService.deleteById(attributeId),HttpStatus.OK);
    }
}
