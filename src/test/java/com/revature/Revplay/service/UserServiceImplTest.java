package com.revature.Revplay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.UserRole;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.impl.UserServiceImpl;

/**
 * JUnit 4 tests for UserServiceImpl.
 */
public class UserServiceImplTest {

    private static final Logger logger = LogManager.getLogger(UserServiceImplTest.class);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUserName("john_doe");
        testUser.setEmail("john@example.com");
        testUser.setRole(UserRole.LISTENER);

        logger.info("UserServiceImplTest setUp complete");
    }

    // ── registerUser ──

    @Test
    public void testRegisterUser_ValidUser_ReturnsSavedUser() {
        logger.info("Running testRegisterUser_ValidUser_ReturnsSavedUser");

        when(userRepository.save(testUser)).thenReturn(testUser);

        User result = userService.registerUser(testUser);

        assertNotNull(result);
        assertEquals("john_doe", result.getUserName());
        verify(userRepository, times(1)).save(testUser);
    }

    // ── findAll ──

    @Test
    public void testFindAll_ReturnsAllUsers() {
        logger.info("Running testFindAll_ReturnsAllUsers");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUserName("jane_doe");

        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser, user2));

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindAll_NoUsers_ReturnsEmptyList() {
        logger.info("Running testFindAll_NoUsers_ReturnsEmptyList");

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userService.findAll();

        assertTrue(result.isEmpty());
    }

    // ── findById ──

    @Test
    public void testFindById_ExistingUser_ReturnsUser() {
        logger.info("Running testFindById_ExistingUser_ReturnsUser");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getUserId());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test(expected = RuntimeException.class)
    public void testFindById_UserNotFound_ThrowsException() {
        logger.info("Running testFindById_UserNotFound_ThrowsException");

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        userService.findById(99L);
    }

    // ── deleteById ──

    @Test
    public void testDeleteById_CallsRepository() {
        logger.info("Running testDeleteById_CallsRepository");

        doNothing().when(userRepository).deleteById(1L);

        userService.deleteById(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}