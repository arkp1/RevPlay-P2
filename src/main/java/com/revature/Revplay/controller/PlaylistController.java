package com.revature.Revplay.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.Revplay.dto.AddSongToPlaylistDTO;
import com.revature.Revplay.dto.ReorderPlaylistSongDTO;
import com.revature.Revplay.entity.Playlist;
import com.revature.Revplay.entity.PlaylistSong;
import com.revature.Revplay.service.PlaylistService;
import com.revature.Revplay.repository.UserRepository;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserRepository userRepository;

    public PlaylistController(PlaylistService playlistService,
    		UserRepository userRepository
    		) {
        this.playlistService = playlistService;
        this.userRepository = userRepository;
    }

    // PLAYLIST CRUD

    @PostMapping("/add")
    public Playlist add(@RequestBody Playlist obj) {
        return playlistService.save(obj);
    }

    @GetMapping("/all")
    public List<Playlist> getAll() {
        return playlistService.findAll();
    }

    // PLAYLIST SONG MANAGEMENT 

    @PostMapping("/{playlistId}/add-song")
    public PlaylistSong addSong(@PathVariable Long playlistId,
                                @RequestBody AddSongToPlaylistDTO request) {
        return playlistService.addSong(playlistId, request.getSongId());
    }

    @DeleteMapping("/{playlistId}/remove-song/{songId}")
    public String removeSong(@PathVariable Long playlistId,
                             @PathVariable Long songId) {
        playlistService.removeSong(playlistId, songId);
        return "Song removed from playlist";
    }

    @PutMapping("/{playlistId}/reorder")
    public PlaylistSong reorderSong(@PathVariable Long playlistId,
                                    @RequestBody ReorderPlaylistSongDTO request) {
        return playlistService.reorderSong(
                playlistId,
                request.getSongId(),
                request.getNewPosition()
        );
    }

    @GetMapping("/{playlistId}/songs")
    public List<PlaylistSong> getSongs(@PathVariable Long playlistId) {
        return playlistService.getPlaylistSongs(playlistId);
    }


    @GetMapping("/user/{targetUserId}")
    public List<Playlist> getUserPlaylists(
            @PathVariable Long targetUserId,
            Authentication authentication
    ) {

        String email = authentication.getName();
        Long requesterUserId = userRepository.findByEmail(email)
                                             .orElseThrow()
                                             .getUserId();

        return playlistService.getUserPlaylists(targetUserId, requesterUserId);
    }
    
    @GetMapping("/public")
    public List<Playlist> getPublicPlaylists() {
        return playlistService.getPublicPlaylists();
    }

    // DELETE PLAYLIST 

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        playlistService.deleteById(id);
        return "Playlist deleted";
    }

    // UPDATE PLAYLIST 

    @PutMapping("/{id}")
    public Playlist update(@PathVariable Long id,
                           @RequestBody Playlist updated) {
        Playlist existing = playlistService.findById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrivacy(updated.getPrivacy());
        return playlistService.save(existing);
    }

    // CREATE WITH USER ID 

    @PostMapping("/create/{userId}")
    public Playlist createForUser(@PathVariable Long userId,
                                  @RequestBody Playlist playlist) {
        return playlistService.createPlaylist(userId, playlist);
    }

}