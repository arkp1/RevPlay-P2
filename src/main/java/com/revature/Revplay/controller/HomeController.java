package com.revature.Revplay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import com.revature.Revplay.entity.User;

import com.revature.Revplay.repository.AlbumRepository;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.FavoriteRepository;
import com.revature.Revplay.repository.PodcastRepository;
import com.revature.Revplay.repository.PlaylistRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.security.JwtUtil;

import io.jsonwebtoken.Claims;

@Controller
public class HomeController {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final PodcastRepository podcastRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final JwtUtil jwtUtil;

    public HomeController(
            SongRepository songRepository,
            ArtistRepository artistRepository,
            AlbumRepository albumRepository,
            PodcastRepository podcastRepository,
            PlaylistRepository playlistRepository,
            UserRepository userRepository,
            FavoriteRepository favoriteRepository,
            JwtUtil jwtUtil
    ) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.podcastRepository = podcastRepository;
        this.playlistRepository = playlistRepository;
		this.userRepository = userRepository;
		this.favoriteRepository = favoriteRepository;
		 this.jwtUtil = jwtUtil; 
		
    }

    @GetMapping("/home")
    public String dashboard(
            @CookieValue(name = "jwt", required = false) String token,
            Model model) {

 
        model.addAttribute("trendingSongs", songRepository.findTop10ByOrderByPlayCountDesc());
        model.addAttribute("popularArtists", artistRepository.findTop10ByOrderByFollowerCountDesc());
        model.addAttribute("latestAlbums", albumRepository.findTop10ByOrderByReleaseDateDesc());
        model.addAttribute("podcasts", podcastRepository.findTop10ByOrderByCreatedAtDesc());


        if (token != null && jwtUtil.validateToken(token)) {
            Claims claims = jwtUtil.extractClaims(token);
            Long userId = claims.get("userId", Long.class);
            String role = claims.get("role", String.class);

            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                model.addAttribute("currentUser", user);
                model.addAttribute("userRole", role);
                model.addAttribute("likedSongs", favoriteRepository.findByUser_UserId(userId));
                model.addAttribute("userPlaylists", playlistRepository.findByUser_UserId(userId));
            }
        }

        return "home";
    }
}