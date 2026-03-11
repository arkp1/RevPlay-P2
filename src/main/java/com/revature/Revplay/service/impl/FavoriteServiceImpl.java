package com.revature.Revplay.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.Revplay.entity.Favorite;
import com.revature.Revplay.entity.compositekey.FavoriteId;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.repository.FavoriteRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.service.FavoriteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private static final Logger logger = LogManager.getLogger(FavoriteServiceImpl.class);

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
                               UserRepository userRepository,
                               SongRepository songRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @Transactional
    @Override
    public Favorite addFavorite(Long userId, Long songId) {
    	logger.info("Adding favorite: userId={}, songId={}", userId, songId);

        if (favoriteRepository.existsByUser_UserIdAndSong_SongId(userId, songId)) {
            throw new RuntimeException("Already favorited");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

     
        if (song.getLikeCount() == null) {
            song.setLikeCount(0L);
        }
        song.setLikeCount(song.getLikeCount() + 1);
        songRepository.save(song);

        Favorite favorite = new Favorite();
        favorite.setId(new FavoriteId(userId, songId));
        favorite.setUser(user);
        favorite.setSong(song);

        return favoriteRepository.save(favorite);
    }

    @Transactional
    @Override
    public void removeFavorite(Long userId, Long songId) {

        FavoriteId id = new FavoriteId(userId, songId);

        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));

        Song song = favorite.getSong();

    
        if (song.getLikeCount() != null && song.getLikeCount() > 0) {
            song.setLikeCount(song.getLikeCount() - 1);
            songRepository.save(song);
        }

        favoriteRepository.deleteById(id);
    }

    @Override
    public List<Favorite> getUserFavorites(Long userId) {
        return favoriteRepository.findByUser_UserId(userId);
    }
}