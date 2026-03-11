package com.revature.Revplay.service;

import java.util.List;

import com.revature.Revplay.entity.Playlist;
import com.revature.Revplay.entity.PlaylistSong;

public interface PlaylistService {

    // Playlist CRUD
    Playlist save(Playlist obj);
    List<Playlist> findAll();
    Playlist findById(Long id);
    void deleteById(Long id);

    // Playlist Song Operations
    PlaylistSong addSong(Long playlistId, Long songId);
    void removeSong(Long playlistId, Long songId);
    PlaylistSong reorderSong(Long playlistId, Long songId, Integer newPosition);
    List<PlaylistSong> getPlaylistSongs(Long playlistId);
    
    List<Playlist> getUserPlaylists(Long targetUserId, Long requesterUserId);
    List<Playlist> getPublicPlaylists();
    Playlist createPlaylist(Long userId, Playlist playlist);
    
    
}