package com.revature.Revplay.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.revature.Revplay.dto.AddSongToPlaylistDTO;
import com.revature.Revplay.dto.ReorderPlaylistSongDTO;
import com.revature.Revplay.entity.PlaylistSong;
import com.revature.Revplay.service.PlaylistService;


@RestController
@RequestMapping("/playlists")
public class PlaylistSongController {

    private final PlaylistService service;

    public PlaylistSongController(PlaylistService service) {
        this.service = service;
    }

    @PostMapping("/{playlistId}/add-song")
    public PlaylistSong addSong(@PathVariable Long playlistId,
                                @RequestBody AddSongToPlaylistDTO request) {
        return service.addSong(playlistId, request.getSongId());
    }

    @DeleteMapping("/{playlistId}/remove-song/{songId}")
    public String removeSong(@PathVariable Long playlistId,
                             @PathVariable Long songId) {
        service.removeSong(playlistId, songId);
        return "Song removed from playlist";
    }

    @PutMapping("/{playlistId}/reorder")
    public PlaylistSong reorder(@PathVariable Long playlistId,
                                @RequestBody ReorderPlaylistSongDTO request) {
        return service.reorderSong(playlistId,
                                   request.getSongId(),
                                   request.getNewPosition());
    }

    @GetMapping("/{playlistId}/songs")
    public List<PlaylistSong> getSongs(@PathVariable Long playlistId) {
        return service.getPlaylistSongs(playlistId);
    }
}
