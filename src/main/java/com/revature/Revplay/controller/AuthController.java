
package com.revature.Revplay.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.Revplay.dto.LoginRequestDTO;
import com.revature.Revplay.dto.RegisterRequestDTO;
import com.revature.Revplay.dto.RegisterResponseDTO;
import com.revature.Revplay.service.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public RegisterResponseDTO register(
            @ModelAttribute RegisterRequestDTO request,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "bannerImage", required = false) MultipartFile bannerImage
    ) throws IOException {
        return service.register(request, profileImage, bannerImage);
    }
    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request) {
        return service.login(request);
    }
    
    @GetMapping("/security-question")
    public String getSecurityQuestion(@RequestParam String email) {
        return service.getSecurityQuestion(email);
    }

    @PostMapping("/verify-answer")
    public boolean verifySecurityAnswer(
            @RequestParam String email,
            @RequestParam String answer
    ) {
        return service.verifySecurityAnswer(email, answer);
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword
    ) {
        service.resetPassword(email, newPassword);
        return "Password updated successfully";
    }
    
 
}
