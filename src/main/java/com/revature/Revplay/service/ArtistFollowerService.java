package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.ArtistFollower;

public interface ArtistFollowerService {

    ArtistFollower follow(Long userId, Long artistId);

    void unfollow(Long userId, Long artistId);

    long getFollowerCount(Long artistId);

    boolean isFollowing(Long userId, Long artistId);

    List<ArtistFollower> getArtistFollowers(Long artistId);

    List<ArtistFollower> getUserFollowing(Long userId);
}
