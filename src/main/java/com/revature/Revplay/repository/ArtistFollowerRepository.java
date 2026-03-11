package com.revature.Revplay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.Revplay.entity.ArtistFollower;

import java.util.List;

public interface ArtistFollowerRepository 
        extends JpaRepository<ArtistFollower, Long> {

    boolean existsByUser_UserIdAndArtist_ArtistId(Long userId, Long artistId);

    void deleteByUser_UserIdAndArtist_ArtistId(Long userId, Long artistId);

    long countByArtist_ArtistId(Long artistId);

    List<ArtistFollower> findByArtist_ArtistId(Long artistId);

    List<ArtistFollower> findByUser_UserId(Long userId);
}