package com.revature.Revplay.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.revature.Revplay.entity.ArtistFollower;
import com.revature.Revplay.service.ArtistFollowerService;

@RestController
@RequestMapping("/artist-follow")
public class ArtistFollowerController {

    private final ArtistFollowerService service;

    public ArtistFollowerController(ArtistFollowerService service) {
        this.service = service;
    }

    @PostMapping("/follow")
    public ArtistFollower follow(@RequestParam Long userId,
                                 @RequestParam Long artistId) {
        return service.follow(userId, artistId);
    }

    @DeleteMapping("/unfollow")
    public String unfollow(@RequestParam Long userId,
                           @RequestParam Long artistId) {
        service.unfollow(userId, artistId);
        return "Unfollowed successfully";
    }

    @GetMapping("/count/{artistId}")
    public long count(@PathVariable Long artistId) {
        return service.getFollowerCount(artistId);
    }

    @GetMapping("/check")
    public boolean check(@RequestParam Long userId,
                         @RequestParam Long artistId) {
        return service.isFollowing(userId, artistId);
    }

    @GetMapping("/artist/{artistId}")
    public List<ArtistFollower> getArtistFollowers(@PathVariable Long artistId) {
        return service.getArtistFollowers(artistId);
    }

    @GetMapping("/user/{userId}")
    public List<ArtistFollower> getUserFollowing(@PathVariable Long userId) {
        return service.getUserFollowing(userId);
    }
}