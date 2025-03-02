package com.example.ecommercebasic.service.user;

import com.example.ecommercebasic.builder.user.CustomerBuilder;
import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.dto.user.AddressRequestDto;
import com.example.ecommercebasic.dto.user.CustomerRequestDto;
import com.example.ecommercebasic.entity.Address;
import com.example.ecommercebasic.entity.user.Customer;
import com.example.ecommercebasic.exception.*;
import com.example.ecommercebasic.repository.user.CustomerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerBuilder customerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final RegexValidation regexValidation;
    private final AddressService addressService;

    public CustomerService(CustomerRepository customerRepository, CustomerBuilder customerBuilder, PasswordEncoder passwordEncoder, RegexValidation regexValidation, AddressService addressService) {
        this.customerRepository = customerRepository;
        this.customerBuilder = customerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.regexValidation = regexValidation;
        this.addressService = addressService;
    }

    public String createCustomer(CustomerRequestDto customerRequestDto) {

        if (!regexValidation.isValidEmail(customerRequestDto.getUsername()))
            throw new InvalidFormatException("Invalid email address");

        if (!regexValidation.isValidPassword(customerRequestDto.getPassword()))
            throw new InvalidFormatException("Invalid password");

        if (existByUsername(customerRequestDto.getUsername()))
            throw new ResourceAlreadyExistException("Username already exists");

        Customer customer = customerBuilder.CustomerRequestDtoToCustomer(customerRequestDto);
        customer.setPassword(passwordEncoder.encode(customerRequestDto.getPassword()));
        customerRepository.save(customer);
        return "Successfully";
    }

    private boolean existByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }


    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Csutomer: "+ApplicationConstant.NOT_FOUND));
    }

    public Customer findById(int id) {
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException(ApplicationConstant.NOT_FOUND));
    }

    public Customer addAddress(AddressRequestDto addressRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            Customer customer = findByUsername(authentication.getName());
            Address address = new Address(customer,addressRequestDto.getCountry(), addressRequestDto.getCity(), addressRequestDto.getAddress());
            addressService.save(address);
            customer.getAddresses().add(address);
            return customerRepository.save(customer);
        }else
            throw new UnAuthorizedException("UnAuthorization Exception");
    }

    public String updateAddress(AddressRequestDto addressRequestDto,int addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = findByUsername(authentication.getName());
        Address address = addressService.findById(addressId);

        if (customer.getAddresses().contains(address)) {
            address.setAddress(addressRequestDto.getAddress());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            addressService.save(address);
            return "Successfully updated address"+ address.getAddress();
        }else
            throw new UnAuthorizedException("Yetkisiz Erişim");

    }

    public String deleteAddress(int addressId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = findByUsername(authentication.getName());
        Address address = addressService.findById(addressId);
        if (customer.getAddresses().contains(address)) {
            customer.getAddresses().remove(address);
            customerRepository.save(customer);
            addressService.delete(address);
            return "Successfully deleted address"+ address.getAddress();

        }else
            throw new UnAuthorizedException("Yetkisiz Erişim");

    }

}
