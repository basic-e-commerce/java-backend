package com.example.ecommercebasic.config.validation;

import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class Converter {

    public static String localDateTimeToTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static LocalDateTime timeToLocalDateTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        try {
            return LocalDateTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException(ApplicationConstant.DATE_TIME_FORMATTER);
        }

    }

    public static LocalTime timeToLocalTime(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            // String'i LocalTime'a dönüştürme
            return LocalTime.parse(time, formatter);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Resource Not Formatted");
        }

    }

    public static String localTimeToTime(LocalTime localTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return localTime.format(formatter);
    }


}
