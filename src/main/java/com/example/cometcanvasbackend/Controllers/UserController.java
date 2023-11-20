package com.example.cometcanvasbackend.Controllers;

import com.example.cometcanvasbackend.Entities.Photo;
import com.example.cometcanvasbackend.Entities.Users;
import com.example.cometcanvasbackend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/comet-canvas/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up/{email}")
    public ResponseEntity<String> registerUser(@PathVariable(name = "email") String email, @RequestParam String password) {
        if (userService.doesUserExist(email)) {
            return new ResponseEntity<>("User with email already exists", HttpStatus.CONFLICT);
        } else {
            userService.saveUser(email, password);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<Users> users = userService.getAllUsers();

        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No users found", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Users user = userService.getUserByEmail(email);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-password/{email}")
    public ResponseEntity<String> updateUserPassword(@PathVariable String email, @RequestParam String newPassword) {
        if (userService.doesUserExist(email)) {
            boolean status = userService.updateUserPassword(email, newPassword);
            if (status) {
                return new ResponseEntity<>("Same Password entered", HttpStatus.OK);
            }
            return new ResponseEntity<>("User password updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete-user/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        if (userService.doesUserExist(email)) {
            userService.deleteUserByEmail(email);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/photos")
    public ResponseEntity<String> displayUserPhotos(@PathVariable Long userId) {
        Optional<Users> optionalUser = userService.getUserById(userId);

        return optionalUser.map(user -> {
            StringBuilder html = new StringBuilder("<html><body>");

            for (Photo photo : user.getPhotos()) {
                html.append("<img src=\"data:image/jpeg;base64,");
                html.append(Base64.getEncoder().encodeToString(photo.getImageData()));
                html.append("\"/><br/>");
            }

            html.append("</body></html>");

            return ResponseEntity.ok(html.toString());
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}