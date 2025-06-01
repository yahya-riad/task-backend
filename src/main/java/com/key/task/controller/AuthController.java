package com.key.task.controller;

import com.key.task.model.UserDto;
import com.key.task.service.UserService;
import com.key.task.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody UserDto userdto) {
        userdto.setPassword(passwordEncoder.encode(userdto.getPassword()));
        userService.createUser(userdto);
        return ResponseEntity.ok("Utilisateur créé !");
    }

    @PostMapping("/token")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        var existingUser = userService.getUserByEmail(userDto.getEmail());

        if (existingUser.isPresent() && passwordEncoder.matches(userDto.getPassword(), existingUser.get().getPassword())) {
            String token = jwtUtil.generateToken(userDto.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }
}

