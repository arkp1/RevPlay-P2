package com.revature.Revplay.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.springframework.stereotype.Service;

import com.revature.Revplay.entity.User;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) {
    	logger.info("Registering user: username={}", user.getUserName());
        return userRepository.save(user);
    }
    
    @Override
    public List<User> findAll() { 
        return userRepository.findAll();
    }
    
    @Override
    public User findById(Long id) {
    	logger.debug("Finding user by id={}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}