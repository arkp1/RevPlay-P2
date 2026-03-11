package com.revature.Revplay.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.revature.Revplay.entity.Favorite;
import com.revature.Revplay.service.FavoriteService;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService service;

    public FavoriteController(FavoriteService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Favorite add(@RequestParam Long userId,
                        @RequestParam Long songId) {
        return service.addFavorite(userId, songId);
    }

    @DeleteMapping("/remove")
    public String remove(@RequestParam Long userId,
                         @RequestParam Long songId) {
        service.removeFavorite(userId, songId);
        return "Removed from favorites";
    }

    @GetMapping("/user/{userId}")
    public List<Favorite> getUserFavorites(@PathVariable Long userId) {
        return service.getUserFavorites(userId);
    }
}
