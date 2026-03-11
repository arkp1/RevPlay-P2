
package com.revature.Revplay.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import com.revature.Revplay.entity.PodcastEpisode;
import com.revature.Revplay.repository.PodcastEpisodeRepository;
import com.revature.Revplay.service.PodcastEpisodeService;

@Service
public class PodcastEpisodeServiceImpl implements PodcastEpisodeService {

    private final PodcastEpisodeRepository repository;

    public PodcastEpisodeServiceImpl(PodcastEpisodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public PodcastEpisode save(PodcastEpisode obj) {
        return repository.save(obj);
    }

    @Override    public List<PodcastEpisode> findAll() {
        return repository.findAll();
    }

    @Override
    public PodcastEpisode findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
