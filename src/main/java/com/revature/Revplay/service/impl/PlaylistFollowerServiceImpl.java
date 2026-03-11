
package com.revature.Revplay.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.revature.Revplay.entity.PlaylistFollower;
import com.revature.Revplay.repository.PlaylistFollowerRepository;
import com.revature.Revplay.service.PlaylistFollowerService;

@Service
public class PlaylistFollowerServiceImpl implements PlaylistFollowerService {

    private final PlaylistFollowerRepository repository;

    public PlaylistFollowerServiceImpl(PlaylistFollowerRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlaylistFollower save(PlaylistFollower follower) {
        return repository.save(follower);
    }

    @Override
    public List<PlaylistFollower> findAll() {
        return repository.findAll();
    }
}
