package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.service.ArtistService;

@Controller
public class ArtistViewController {

    private final ArtistService artistService;
    private final SongRepository songRepository;

    public ArtistViewController(ArtistService artistService, SongRepository songRepository) {
        this.artistService = artistService;
        this.songRepository = songRepository;
    }

    @GetMapping("/view/artist/{id}")
    public String artistDetail(@PathVariable Long id, Model model) {
        Artist artist = artistService.findById(id);
        List<Song> songs = songRepository.findByArtist_ArtistId(id);
        model.addAttribute("artist", artist);
        model.addAttribute("songs", songs);
        return "artist-detail";
    }
}