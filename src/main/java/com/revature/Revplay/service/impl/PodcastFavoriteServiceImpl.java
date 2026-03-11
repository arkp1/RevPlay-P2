
package com.revature.Revplay.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.revature.Revplay.entity.PodcastFavorite;
import com.revature.Revplay.repository.PodcastFavoriteRepository;
import com.revature.Revplay.service.PodcastFavoriteService;

@Service
public class PodcastFavoriteServiceImpl implements PodcastFavoriteService {

    private final PodcastFavoriteRepository repository;

    public PodcastFavoriteServiceImpl(PodcastFavoriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public PodcastFavorite save(PodcastFavorite favorite) {
        return repository.save(favorite);
    }

    @Override
    public List<PodcastFavorite> findAll() {
        return repository.findAll();
    }
}
