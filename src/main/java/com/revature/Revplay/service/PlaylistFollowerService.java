
package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.PlaylistFollower;

public interface PlaylistFollowerService {
    PlaylistFollower save(PlaylistFollower follower);
    List<PlaylistFollower> findAll();
}
