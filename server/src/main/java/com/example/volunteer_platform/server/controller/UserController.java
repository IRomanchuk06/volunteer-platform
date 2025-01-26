package com.example.volunteer_platform.server.controller;

import com.example.volunteer_platform.server.mapper.UserMapper;
import com.example.volunteer_platform.server.model.User;
import com.example.volunteer_platform.server.service.UserService;
import com.example.volunteer_platform.shared_dto.UpdateUserDTO;
import com.example.volunteer_platform.shared_dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public abstract class UserController<U extends User> {

    protected final UserService<U> service;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService<U> userService, UserMapper userMapper) {
        this.service = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = service.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = service.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with username: " + username));
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @RequestBody UpdateUserDTO updateRequest) {
        User updatedUser = service.updateUser(
                updateRequest.getEmail(),
                updateRequest.getUsername(),
                updateRequest.getOldPassword(),
                updateRequest.getNewPassword());

        UserResponseDTO response = userMapper.toUserResponseDTO(updatedUser);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        boolean isDeleted = service.deleteUser(email);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }
    }
}
