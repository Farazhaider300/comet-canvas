//package com.example.cometcanvasbackend.SecurityConfiguration;
//
//import com.example.cometcanvasbackend.Entities.Photo;
//import com.example.cometcanvasbackend.Entities.Users;
//import com.example.cometcanvasbackend.Repositories.PhotoRepository;
//import com.example.cometcanvasbackend.Repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataPopulator implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final PhotoRepository photoRepository;
//
//    @Autowired
//    public DataPopulator(UserRepository userRepository, PhotoRepository photoRepository) {
//        this.userRepository = userRepository;
//        this.photoRepository = photoRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Create a sample user
//        Users user1 = new Users("anasophia@gmail.com", new BCryptPasswordEncoder().encode("abcd1234"));
//        user1.setRole("USER");
//        userRepository.save(user1);
//
//        Users user2 = new Users("mansoor@gmail.com", new BCryptPasswordEncoder().encode("abcd1234"));
//        user2.setRole("USER");
//        userRepository.save(user2);
//
//        // Create sample photos associated with the user
//        Photo photo1 = new Photo("Nature", "John Doe", "Beautiful landscape", new byte[]{/* your image data */});
//        photo1.setUser(user1);
//        photoRepository.save(photo1);
//
//        Photo photo2 = new Photo("Cityscape", "Jane Doe", "Urban environment", new byte[]{/* your image data */});
//        photo2.setUser(user2);
//        photoRepository.save(photo2);
//    }
//}
