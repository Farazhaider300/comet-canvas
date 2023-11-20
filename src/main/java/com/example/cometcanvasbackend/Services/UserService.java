package com.example.cometcanvasbackend.Services;

import com.example.cometcanvasbackend.Entities.Users;
import com.example.cometcanvasbackend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Users user;

    public boolean doesUserExist(String email) {
        user = userRepository.findByEmail(email);
        return user != null;
    }


    public void saveUser(String email, String password) {
        String encryptedPassword = passwordEncoder.encode(password);
        Users newUsers = new Users(email, encryptedPassword);
        newUsers.setRole("USER");
        userRepository.save(newUsers);
    }

    public Users getUserByEmail(String email) {
        return user = userRepository.findByEmail(email);
    }

    public boolean updateUserPassword(String email, String newPassword) {
        user = userRepository.findByEmail(email);
        newPassword = passwordEncoder.encode(newPassword);
        if (Objects.equals(user.getPassword(), newPassword)) {
            return true;
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        return false;
    }

    public void deleteUserByEmail(String email) {
        Users user = getUserByEmail(email);
        userRepository.deleteById(user.getId());
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Users> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
