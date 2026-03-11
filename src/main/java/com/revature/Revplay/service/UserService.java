package com.revature.Revplay.service;

import java.util.List;

import com.revature.Revplay.entity.User;

public interface UserService {
	
    User registerUser(User user);
    
    List<User> findAll(); 
    
    User findById(Long id);
    
    void deleteById(Long id);

}