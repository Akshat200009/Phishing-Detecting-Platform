package com.phishing.Services;

import com.phishing.DTO.UpdateProfileRequest;
import com.phishing.Entities.User;
import com.phishing.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User updateProfile(
            User user,
            UpdateProfileRequest request) {

        if (!user.getEmail().equalsIgnoreCase(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new IllegalArgumentException(
                    "Email is already registered"
            );
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }
}