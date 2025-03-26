package com.quiszerver.controller;

import com.quiszerver.model.User;
import com.quiszerver.repository.UserRepository;
import com.quiszerver.requests.LoginRequest;
import com.quiszerver.response.LoginResponse;
import com.quiszerver.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    @PostMapping("/register")
    public User newUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername() != null ? userDetails.getUsername() : user.getUsername());
                    user.setEmail(userDetails.getEmail() != null ? userDetails.getEmail() : user.getEmail());
                    user.setPhone((Integer) (userDetails.getPhone() != null ? userDetails.getPhone() : user.getPhone())); // Sửa lỗi ở đây
                    user.setIs_teacher(userDetails.getIs_teacher() != null ? userDetails.getIs_teacher() : user.getIs_teacher());
                    User updatedUser = userRepository.save(user);
                    return ResponseEntity.ok(updatedUser);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.authenticateUser(loginRequest);

        if (loginResponse.isAuthenticated()) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResponse);
        }
    }
}
