package com.revature.Revplay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.revature.Revplay.dto.LoginRequestDTO;
import com.revature.Revplay.dto.RegisterRequestDTO;
import com.revature.Revplay.dto.RegisterResponseDTO;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.UserRole;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.FileUploadService;
import com.revature.Revplay.service.impl.AuthServiceImpl;

public class AuthServiceImplTest {

    private static final Logger logger = LogManager.getLogger(AuthServiceImplTest.class);

    @Mock private UserRepository userRepository;
    @Mock private ArtistRepository artistRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private FileUploadService fileUploadService;
    @Mock private MultipartFile profileImageMock;
    @Mock private MultipartFile bannerImageMock;

    @InjectMocks
    private AuthServiceImpl authService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("AuthServiceImplTest setUp complete");
    }

    @Test
    public void testRegister_ListenerRole_ReturnsResponseDTO() throws IOException {
        logger.info("Running testRegister_ListenerRole_ReturnsResponseDTO");

        RegisterRequestDTO req = new RegisterRequestDTO();
        req.setUserName("john_doe");
        req.setEmail("john@example.com");
        req.setPassword("secret123");
        req.setRole(UserRole.LISTENER);
        req.setSecurityQuestion("Favourite color?");
        req.setSecurityAnswer("blue");

        when(fileUploadService.saveProfileImage(any())).thenReturn(null);
        when(passwordEncoder.encode("secret123")).thenReturn("hashed_secret");

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setUserName("john_doe");
        savedUser.setEmail("john@example.com");
        savedUser.setRole(UserRole.LISTENER);
        savedUser.setUserPassword("hashed_secret");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        RegisterResponseDTO response = authService.register(req, profileImageMock, bannerImageMock);

        assertNotNull("Response must not be null", response);
        assertEquals("john_doe", response.getUserName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals("LISTENER", response.getRole());
        verify(userRepository, times(1)).save(any(User.class));
        verify(artistRepository, never()).save(any());

        logger.info("testRegister_ListenerRole_ReturnsResponseDTO PASSED");
    }

    @Test
    public void testRegister_ArtistRole_SavesArtistEntity() throws IOException {
        logger.info("Running testRegister_ArtistRole_SavesArtistEntity");

        RegisterRequestDTO req = new RegisterRequestDTO();
        req.setUserName("artist_jane");
        req.setEmail("jane@music.com");
        req.setPassword("music123");
        req.setRole(UserRole.ARTIST);
        req.setArtistName("Jane Music");
        req.setBio("Pop artist");
        req.setGenre("Pop");
        req.setSecurityQuestion("City?");
        req.setSecurityAnswer("NYC");

        when(fileUploadService.saveProfileImage(any())).thenReturn("/images/jane.png");
        when(fileUploadService.saveBannerImage(any())).thenReturn("/images/jane-banner.png");
        when(passwordEncoder.encode(any())).thenReturn("hashed");

        User savedUser = new User();
        savedUser.setUserId(2L);
        savedUser.setUserName("artist_jane");
        savedUser.setEmail("jane@music.com");
        savedUser.setRole(UserRole.ARTIST);
        savedUser.setUserPassword("hashed");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        RegisterResponseDTO response = authService.register(req, profileImageMock, bannerImageMock);

        assertNotNull(response);
        assertEquals("ARTIST", response.getRole());
        verify(artistRepository, times(1)).save(any());

        logger.info("testRegister_ArtistRole_SavesArtistEntity PASSED");
    }

    @Test
    public void testLogin_ValidCredentials_ReturnsToken() {
        logger.info("Running testLogin_ValidCredentials_ReturnsToken");

        User user = new User();
        user.setUserId(1L);
        user.setEmail("john@example.com");
        user.setUserPassword("hashed_secret");
        user.setRole(UserRole.LISTENER);

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret123", "hashed_secret")).thenReturn(true);

        LoginRequestDTO req = new LoginRequestDTO();
        req.setEmail("john@example.com");
        req.setPassword("secret123");

        String token = authService.login(req);

        assertNotNull("Token must not be null", token);
        assertFalse("Token must not be empty", token.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testLogin_WrongPassword_ThrowsException() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setUserPassword("hashed_secret");
        user.setRole(UserRole.LISTENER);

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_pass", "hashed_secret")).thenReturn(false);

        LoginRequestDTO req = new LoginRequestDTO();
        req.setEmail("john@example.com");
        req.setPassword("wrong_pass");

        authService.login(req);
    }

    @Test(expected = RuntimeException.class)
    public void testLogin_UserNotFound_ThrowsException() {
        when(userRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        LoginRequestDTO req = new LoginRequestDTO();
        req.setEmail("ghost@example.com");
        req.setPassword("any");

        authService.login(req);
    }

    @Test
    public void testGetSecurityQuestion_ExistingUser_ReturnsQuestion() {
        User user = new User();
        user.setSecurityQuestion("Favourite color?");
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        assertEquals("Favourite color?", authService.getSecurityQuestion("john@example.com"));
    }

    @Test
    public void testVerifySecurityAnswer_CorrectAnswer_ReturnsTrue() {
        User user = new User();
        user.setSecurityAnswer("blue");
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        assertTrue(authService.verifySecurityAnswer("john@example.com", "blue"));
    }

    @Test
    public void testVerifySecurityAnswer_WrongAnswer_ReturnsFalse() {
        User user = new User();
        user.setSecurityAnswer("blue");
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        assertFalse(authService.verifySecurityAnswer("john@example.com", "red"));
    }

    @Test
    public void testResetPassword_ExistingUser_UpdatesPassword() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setUserPassword("old_hash");
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("newPass123")).thenReturn("new_hash");
        when(userRepository.save(any(User.class))).thenReturn(user);

        authService.resetPassword("john@example.com", "newPass123");

        verify(userRepository, times(1)).save(user);
        assertEquals("new_hash", user.getUserPassword());
    }
}