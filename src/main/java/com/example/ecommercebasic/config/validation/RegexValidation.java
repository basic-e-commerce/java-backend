package com.example.ecommercebasic.config.validation;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@Component
public class RegexValidation {

    @Value("${regex.email}")
    public String emailRegex;
    @Value("${regex.password}")
    public String passwordRegex;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;


    public boolean isValidEmail(String email){

        String EMAIL_REGEX = emailRegex;
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = pattern.matcher(email);

        return emailMatcher.matches();
    }

    public boolean isValidPassword(String password){

        String PASSWORD_REGEX = passwordRegex;
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher passwordMatcher = pattern.matcher(password);

        return passwordMatcher.matches();
    }

/**
    public static boolean isValidPhoneNumber(String phoneNumber) {

        String PHONE_NUMBER_REGEX = "^\\d{10}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher passwordMatchers = pattern.matcher(phoneNumber);

        return passwordMatchers.matches();
    }

    public static boolean isFourDigitNumber(String input) {
        String regex = "^\\d{4}$";
        return input.matches(regex);
    }**/

    public static String sanitize(String unsafeHtml) {
        return Jsoup.clean(unsafeHtml, Safelist.relaxed());
    }

    public String generateUniqueOrderCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
}
