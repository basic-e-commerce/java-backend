package com.example.ecommercebasic.service.user;

import com.example.ecommercebasic.dto.user.AddressRequestDto;
import com.example.ecommercebasic.entity.Address;
import com.example.ecommercebasic.entity.user.User;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.user.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }


    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    List<Address> getAddressByUserId(User user) {
        return addressRepository.findByUser(user);
    }

    public Address findById(int addressId) {
        return addressRepository.findById(addressId).orElseThrow(()-> new NotFoundException("Address not found"));
    }

    public void delete(Address address) {
        addressRepository.delete(address);
    }
}
