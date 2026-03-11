package com.revature.Revplay.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.entity.Favorite;
import com.revature.Revplay.entity.ListeningHistory;
import com.revature.Revplay.entity.Playlist;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.FavoriteRepository;
import com.revature.Revplay.repository.ListeningHistoryRepository;
import com.revature.Revplay.repository.PlaylistRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.security.JwtUtil;

import io.jsonwebtoken.Claims;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final FavoriteRepository favoriteRepository;
    private final PlaylistRepository playlistRepository;
    private final ListeningHistoryRepository listeningHistoryRepository;
    private final SongRepository songRepository;
    private final JwtUtil jwtUtil;

    public ProfileController(
            UserRepository userRepository,
            ArtistRepository artistRepository,
            FavoriteRepository favoriteRepository,
            PlaylistRepository playlistRepository,
            ListeningHistoryRepository listeningHistoryRepository,
            SongRepository songRepository,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
        this.favoriteRepository = favoriteRepository;
        this.playlistRepository = playlistRepository;
        this.listeningHistoryRepository = listeningHistoryRepository;
        this.songRepository = songRepository;
        this.jwtUtil = jwtUtil;
    }

    // Explicit mapping so Spring matches /profile/login here BEFORE the wildcard below
    @GetMapping("/profile/login")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/profile/{userId}")
    public String profile(
            @PathVariable Long userId,
            @CookieValue(name = "jwt", required = false) String token,
            Model model) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Favorite> favorites =
                favoriteRepository.findByUser_UserId(userId);

        List<Playlist> playlists =
                playlistRepository.findByUser_UserId(userId);

        List<ListeningHistory> history =
                listeningHistoryRepository.findTop50ByUser_UserIdOrderByPlayedAtDesc(userId);

        model.addAttribute("user", user);
        model.addAttribute("favorites", favorites);
        model.addAttribute("playlists", playlists);
        model.addAttribute("history", history);
        model.addAttribute("isListener", user.getRole().name().equals("LISTENER"));

       
        if (token != null && jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.extractClaims(token);
            Long loggedInUserId = claims.get("userId", Long.class);
            String role = claims.get("role", String.class);
            User loggedInUser = userRepository.findById(loggedInUserId).orElse(null);
            if (loggedInUser != null) {
                model.addAttribute("currentUser", loggedInUser);
                model.addAttribute("userRole", role);
            }
        }

        if(user.getRole().name().equals("ARTIST")) {

            Artist artist =
                    artistRepository.findByUser_UserId(userId).orElse(null);

            List<Song> songs =
                    songRepository.findByArtist_ArtistId(artist.getArtistId());

            model.addAttribute("artist", artist);
            model.addAttribute("songs", songs);
        }

        return "profile";
    }
}