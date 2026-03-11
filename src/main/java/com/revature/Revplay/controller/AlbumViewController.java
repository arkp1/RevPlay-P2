package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.revature.Revplay.entity.Album;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.service.AlbumService;

@Controller
public class AlbumViewController {

    private final AlbumService albumService;
    private final SongRepository songRepository;

    public AlbumViewController(AlbumService albumService, SongRepository songRepository) {
        this.albumService = albumService;
        this.songRepository = songRepository;
    }

    @GetMapping("/view/album/{id}")
    public String albumDetail(@PathVariable Long id, Model model) {
        Album album = albumService.findById(id);
        List<Song> songs = songRepository.findByArtist_ArtistId(album.getArtist().getArtistId());
        model.addAttribute("album", album);
        model.addAttribute("songs", songs);
        return "album-detail";
    }
}