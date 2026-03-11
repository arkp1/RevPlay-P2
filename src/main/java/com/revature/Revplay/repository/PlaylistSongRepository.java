package com.revature.Revplay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.Revplay.entity.PlaylistSong;
import com.revature.Revplay.entity.compositekey.PlaylistSongId;

public interface PlaylistSongRepository 
        extends JpaRepository<PlaylistSong, PlaylistSongId> {

    List<PlaylistSong> findByPlaylist_PlaylistIdOrderByPositionAsc(Long playlistId);

    Integer countByPlaylist_PlaylistId(Long playlistId);
}