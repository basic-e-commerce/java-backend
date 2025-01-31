package com.example.ecommercebasic.service.user;

import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.entity.user.User;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new NotFoundException(ApplicationConstant.NOT_FOUND));
    }
}
