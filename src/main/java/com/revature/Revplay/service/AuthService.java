
package com.revature.Revplay.service;

import com.revature.Revplay.dto.RegisterRequestDTO;
import com.revature.Revplay.dto.RegisterResponseDTO;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.revature.Revplay.dto.LoginRequestDTO;


public interface AuthService {
  
	RegisterResponseDTO register(RegisterRequestDTO request, MultipartFile profileImage, MultipartFile bannerImage) throws IOException;

        String login(LoginRequestDTO request);
        
        String getSecurityQuestion(String email);
        
        boolean verifyEmail(String email);

        boolean verifySecurityAnswer(String email, String answer);

        void resetPassword(String email, String newPassword);
        
    }
    
 
