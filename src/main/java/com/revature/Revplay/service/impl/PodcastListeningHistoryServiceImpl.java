
package com.revature.Revplay.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.revature.Revplay.entity.PodcastListeningHistory;
import com.revature.Revplay.repository.PodcastListeningHistoryRepository;
import com.revature.Revplay.service.PodcastListeningHistoryService;

@Service
public class PodcastListeningHistoryServiceImpl implements PodcastListeningHistoryService {

    private final PodcastListeningHistoryRepository repository;

    public PodcastListeningHistoryServiceImpl(PodcastListeningHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public PodcastListeningHistory save(PodcastListeningHistory history) {
        return repository.save(history);
    }

    @Override
    public List<PodcastListeningHistory> findAll() {
        return repository.findAll();
    }
}
