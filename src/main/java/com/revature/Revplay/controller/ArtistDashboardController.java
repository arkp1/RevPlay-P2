package com.revature.Revplay.controller;

import com.revature.Revplay.entity.Artist;

import com.revature.Revplay.entity.User;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.entity.Album;
import com.revature.Revplay.repository.AlbumRepository;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.PodcastRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.security.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ArtistDashboardController {
	
	private final UserRepository userRepository;

    private final JwtUtil jwtUtil;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final PodcastRepository podcastRepository;

    public ArtistDashboardController(JwtUtil jwtUtil,
            ArtistRepository artistRepository,
            AlbumRepository albumRepository,
            SongRepository songRepository,
            PodcastRepository podcastRepository,
            UserRepository userRepository) {
this.jwtUtil = jwtUtil;
this.artistRepository = artistRepository;
this.albumRepository = albumRepository;
this.songRepository = songRepository;
this.podcastRepository = podcastRepository;
this.userRepository = userRepository;
}
    @GetMapping("/artist-dashboard")
    public String artistDashboard(
            @CookieValue(name = "jwt", required = false) String token,
            Model model) {

        if (token == null || !jwtUtil.validateToken(token)) {
            return "redirect:/login";
        }

        Claims claims = jwtUtil.extractClaims(token);
        String role = claims.get("role", String.class);
        Long userId = claims.get("userId", Long.class);

        if (!"ARTIST".equals(role)) {
            return "redirect:/home";
        }

        Artist artist = artistRepository.findByUser_UserId(userId).orElse(null);
        if (artist == null) {
            return "redirect:/home";
        }

        List<Album> myAlbums = albumRepository.findByArtist_User_UserId(userId);
        List<com.revature.Revplay.entity.Song> mySongs = songRepository.findByArtist_ArtistId(artist.getArtistId());
        List<com.revature.Revplay.entity.Podcast> myPodcasts = podcastRepository.findByArtist_ArtistId(artist.getArtistId());

        model.addAttribute("artist", artist);
        model.addAttribute("myAlbums", myAlbums);
        model.addAttribute("mySongs", mySongs);
        model.addAttribute("myPodcasts", myPodcasts);
        
        User currentUser = userRepository.findById(userId).orElse(null);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userRole", role);  

        return "artist-dashboard";
    }
}