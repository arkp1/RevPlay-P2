package com.revature.Revplay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import com.revature.Revplay.entity.User;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.security.JwtUtil;

import io.jsonwebtoken.Claims;

@Controller
public class PlaylistViewController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public PlaylistViewController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/playlists")
    public String playlistsPage(
            @CookieValue(name = "jwt", required = false) String token,
            Model model) {

        if (token != null && jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.extractClaims(token);
            Long userId = claims.get("userId", Long.class);
            String role = claims.get("role", String.class);

            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                model.addAttribute("currentUser", user);
                model.addAttribute("userRole", role);
            }
        }

        return "playlists";
    }
}