package com.revature.Revplay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.service.SongService;

import jakarta.transaction.Transactional;

@Controller
public class SongViewController {

    private final SongService songService;

    public SongViewController(SongService songService) {
        this.songService = songService;
    }
    
    @Transactional
    @GetMapping("/view/songs/{id}")
    public String songDetail(@PathVariable Long id, Model model) {
        Song song = songService.findById(id);
        model.addAttribute("song", song);
        return "song-detail";
    }
}