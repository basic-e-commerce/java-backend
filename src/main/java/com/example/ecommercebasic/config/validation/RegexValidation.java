package com.example.ecommercebasic.config.validation;

import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.exception.InvalidFormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexValidation {


    public static boolean isValidEmail(String email){

        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = pattern.matcher(email);

        return emailMatcher.matches();
    }

    public static boolean isValidPasswword(String password){

        String PASSWORD_REGEX = "^(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = pattern.matcher(password);

        return passwordMatcher.matches();
    }


    public static boolean isValidPhoneNumber(String phoneNumber) {

        String PHONE_NUMBER_REGEX = "^\\d{10}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher passwordMatchers = pattern.matcher(phoneNumber);

        return passwordMatchers.matches();
    }

    public static boolean isFourDigitNumber(String input) {
        String regex = "^\\d{4}$";
        return input.matches(regex);
    }

    public static boolean isEmailOrPhone(String username){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        String phoneNoRegex = "^\\d{10}$";

        if (username.matches(emailRegex))
            return true;
        else if (username.matches(phoneNoRegex))
            return false;
        else
            throw new InvalidFormatException(ApplicationConstant.WRONG_CREDENTIALS);

    }

}
