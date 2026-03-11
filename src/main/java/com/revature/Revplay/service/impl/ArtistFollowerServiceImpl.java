package com.revature.Revplay.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.entity.ArtistFollower;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.repository.ArtistFollowerRepository;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.ArtistFollowerService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArtistFollowerServiceImpl implements ArtistFollowerService {

    private final ArtistFollowerRepository repository;
    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private static final Logger logger = LogManager.getLogger(ArtistFollowerServiceImpl.class);
    
    public ArtistFollowerServiceImpl(
            ArtistFollowerRepository repository,
            UserRepository userRepository,
            ArtistRepository artistRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public ArtistFollower follow(Long userId, Long artistId) {
    	logger.info("User id={} following artist id={}", userId, artistId);

        if (repository.existsByUser_UserIdAndArtist_ArtistId(userId, artistId)) {
            throw new RuntimeException("Already following");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        ArtistFollower follower = new ArtistFollower();
        follower.setUser(user);
        follower.setArtist(artist);

        // SAFE increment
        artist.setFollowerCount(
                artist.getFollowerCount() == null ? 1 : artist.getFollowerCount() + 1
        );

        repository.save(follower);
        artistRepository.save(artist);

        return follower;
    }

    @Override
    public void unfollow(Long userId, Long artistId) {
    	logger.info("User id={} unfollowing artist id={}", userId, artistId);
    	

        if (!repository.existsByUser_UserIdAndArtist_ArtistId(userId, artistId)) {
            return; // already not following
        }

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        repository.deleteByUser_UserIdAndArtist_ArtistId(userId, artistId);

        Long count = artist.getFollowerCount();

        if (count != null && count > 0) {
            artist.setFollowerCount(count - 1);
            artistRepository.save(artist);
        }
    }


    @Override
    public long getFollowerCount(Long artistId) {
        return repository.countByArtist_ArtistId(artistId);
    }

    @Override
    public boolean isFollowing(Long userId, Long artistId) {
        return repository.existsByUser_UserIdAndArtist_ArtistId(userId, artistId);
    }

    @Override
    public List<ArtistFollower> getArtistFollowers(Long artistId) {
        return repository.findByArtist_ArtistId(artistId);
    }

    @Override
    public List<ArtistFollower> getUserFollowing(Long userId) {
        return repository.findByUser_UserId(userId);
    }
}