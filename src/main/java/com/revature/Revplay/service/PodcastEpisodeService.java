package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.PodcastEpisode;

public interface PodcastEpisodeService {

    PodcastEpisode save(PodcastEpisode obj);

    List<PodcastEpisode> findAll();

    PodcastEpisode findById(Long id);

    void deleteById(Long id);
}