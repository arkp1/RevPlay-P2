package com.revature.Revplay.service.impl;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.repository.UserRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.Revplay.entity.Playlist;
import com.revature.Revplay.entity.PlaylistPrivacy;
import com.revature.Revplay.entity.PlaylistSong;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.entity.compositekey.PlaylistSongId;
import com.revature.Revplay.repository.PlaylistRepository;
import com.revature.Revplay.repository.PlaylistSongRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.service.PlaylistService;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;
    

    public PlaylistServiceImpl(PlaylistRepository playlistRepository,
            PlaylistSongRepository playlistSongRepository,
            SongRepository songRepository,
            UserRepository userRepository) {
this.playlistRepository = playlistRepository;
this.playlistSongRepository = playlistSongRepository;
this.songRepository = songRepository;
this.userRepository = userRepository;
}

    //PLAYLIST CRUD

    @Override
    public Playlist save(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Override
    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist findById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
    }

    @Override
    public void deleteById(Long id) {
        playlistRepository.deleteById(id);
    }

    // PLAYLIST SONG 

    @Transactional
    @Override
    public PlaylistSong addSong(Long playlistId, Long songId) {

        Playlist playlist = findById(playlistId);

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        int position =
                playlistSongRepository.countByPlaylist_PlaylistId(playlistId) + 1;

        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setId(new PlaylistSongId(playlistId, songId));
        playlistSong.setPlaylist(playlist);
        playlistSong.setSong(song);
        playlistSong.setPosition(position);

        return playlistSongRepository.save(playlistSong);
    }

    @Override
    public void removeSong(Long playlistId, Long songId) {
        playlistSongRepository.deleteById(new PlaylistSongId(playlistId, songId));
    }

    @Transactional
    @Override
    public PlaylistSong reorderSong(Long playlistId,
                                    Long songId,
                                    Integer newPosition) {

        
        PlaylistSong ps = playlistSongRepository
                .findById(new PlaylistSongId(playlistId, songId))
                .orElseThrow(() -> new RuntimeException("Playlist song not found"));

        int oldPosition = ps.getPosition();

        // swap
        List<PlaylistSong> allSongs = playlistSongRepository
                .findByPlaylist_PlaylistIdOrderByPositionAsc(playlistId);

        allSongs.stream()
                .filter(other -> other.getPosition() == newPosition
                        && !other.getSong().getSongId().equals(songId))
                .findFirst()
                .ifPresent(other -> {
                    other.setPosition(oldPosition);
                    playlistSongRepository.save(other);
                });

        ps.setPosition(newPosition);
        return playlistSongRepository.save(ps);
    }

    @Override
    public List<PlaylistSong> getPlaylistSongs(Long playlistId) {
        return playlistSongRepository
                .findByPlaylist_PlaylistIdOrderByPositionAsc(playlistId);
    }
    
    // privacy Logic
    @Override
    public List<Playlist> getUserPlaylists(Long targetUserId, Long requesterUserId) {

        if (targetUserId.equals(requesterUserId)) {
            return playlistRepository.findByUser_UserId(targetUserId);
        }

        return playlistRepository.findByUser_UserIdAndPrivacy(targetUserId, PlaylistPrivacy.PUBLIC);
    }

    @Override
    public List<Playlist> getPublicPlaylists() {
        return playlistRepository.findByPrivacy(PlaylistPrivacy.PUBLIC);
    }
    
    @Override
    public Playlist createPlaylist(Long userId, Playlist playlist) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        playlist.setUser(user);

        return playlistRepository.save(playlist);
    }
}