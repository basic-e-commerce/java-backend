package com.example.ecommercebasic.dto.product.order;

public class OrderDeliveryRequestDto {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String identityNo;
    private String address;
    private String apartNo;
    private String postalCode;
    private String city;
    private String country;


    public OrderDeliveryRequestDto(String name, String surname, String email, String phone, String identityNo, String address, String apartNo, String postalCode, String city, String country) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.identityNo = identityNo;
        this.address = address;
        this.apartNo = apartNo;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartNo() {
        return apartNo;
    }

    public void setApartNo(String apartNo) {
        this.apartNo = apartNo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
