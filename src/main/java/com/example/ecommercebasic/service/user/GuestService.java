package com.example.ecommercebasic.service.user;

import com.example.ecommercebasic.entity.user.Guest;
import com.example.ecommercebasic.repository.user.GuestRepository;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest createGuest(Guest guest) {
        return guestRepository.save(guest);
    }
}
