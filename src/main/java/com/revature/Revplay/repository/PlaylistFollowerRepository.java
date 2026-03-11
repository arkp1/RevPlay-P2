
package com.revature.Revplay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.Revplay.entity.PlaylistFollower;
import com.revature.Revplay.entity.compositekey.PlaylistFollowerId;

public interface PlaylistFollowerRepository 
        extends JpaRepository<PlaylistFollower, PlaylistFollowerId> {}
