


package com.revature.Revplay.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.Revplay.dto.RegisterRequestDTO;
import com.revature.Revplay.dto.RegisterResponseDTO;
import com.revature.Revplay.dto.LoginRequestDTO;
import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.UserRole;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.security.JwtUtil;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.service.AuthService;
import com.revature.Revplay.service.FileUploadService;


import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final FileUploadService fileUploadService;
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);
    

    public AuthServiceImpl(UserRepository userRepository,
            ArtistRepository artistRepository,
            PasswordEncoder passwordEncoder,
            FileUploadService fileUploadService) {
this.userRepository = userRepository;
this.artistRepository = artistRepository;
this.passwordEncoder = passwordEncoder;
this.jwtUtil = new JwtUtil();
this.fileUploadService = fileUploadService;
}
   
    
    @Transactional
    @Override

    public RegisterResponseDTO register(RegisterRequestDTO request, MultipartFile profileImage, MultipartFile bannerImage) throws IOException {
    	logger.info("Registering new user: email={}, role={}", request.getEmail(), request.getRole());
        User user = new User();

        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setUserPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(
            request.getRole() != null ? request.getRole() : UserRole.LISTENER
        );

        user.setBio(request.getBio());

        String imagePath = fileUploadService.saveProfileImage(profileImage);

        if(imagePath != null){
            user.setProfileImage(imagePath);
        }

        user.setSecurityQuestion(request.getSecurityQuestion());
        user.setSecurityAnswer(request.getSecurityAnswer());

        User savedUser = userRepository.save(user);

        if(savedUser.getRole() == UserRole.ARTIST){

            Artist artist = new Artist();

            artist.setUser(savedUser);
            artist.setArtistName(request.getArtistName());
            artist.setBio(request.getBio());
            artist.setGenre(request.getGenre());
            String bannerPath = fileUploadService.saveBannerImage(bannerImage);
            if (bannerPath != null) {
                artist.setBannerImage(bannerPath);
            }
            artist.setInstagramLink(request.getInstagramLink());
            artist.setTwitterLink(request.getTwitterLink());
            artist.setSpotifyLink(request.getSpotifyLink());
            artist.setWebsiteLink(request.getWebsiteLink());

            artistRepository.save(artist);
        }

        return new RegisterResponseDTO(
            savedUser.getUserId(),
            savedUser.getUserName(),
            savedUser.getEmail(),
            savedUser.getRole().name()
        );
    }
    
    
    @Override
    public boolean verifyEmail(String email) {

        logger.info("Verifying email availability: {}", email);

        if (email == null || email.trim().isEmpty()) {
            logger.warn("Email verification attempted with empty value");
            return false;
        }

        boolean exists = userRepository.findByEmail(email).isPresent();

        if (exists) {
            logger.info("Email already exists in system: {}", email);
            return false;
        }

        logger.info("Email is available for registration: {}", email);
        return true;
    }
    

    @Override
    public String login(LoginRequestDTO request) {
    	logger.info("Login attempt for email={}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getUserPassword())) {
            throw new RuntimeException("Invalid password");
        }

//        return jwtUtil.generateToken(user.getEmail());
        return jwtUtil.generateToken(user.getEmail(), user.getUserId(), user.getRole().name());
    }
    
    @Override
    public String getSecurityQuestion(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getSecurityQuestion();
    }

    @Override
    public boolean verifySecurityAnswer(String email, String answer) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getSecurityAnswer().equalsIgnoreCase(answer);
    }

    @Override
    public void resetPassword(String email, String newPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }

}
