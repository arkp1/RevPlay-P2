
package com.revature.Revplay.service.impl;


import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.revature.Revplay.entity.Podcast;
import com.revature.Revplay.repository.PodcastRepository;
import com.revature.Revplay.service.PodcastService;

@Service
public class PodcastServiceImpl implements PodcastService {

    private final PodcastRepository repository;
    private static final Logger logger = LogManager.getLogger(PodcastServiceImpl.class);

    public PodcastServiceImpl(PodcastRepository repository) {
        this.repository = repository;
    }

    @Override
    public Podcast save(Podcast obj) {
    	logger.info("Saving podcast: title={}", obj.getTitle());
        return repository.save(obj);
    }

    @Override
    public List<Podcast> findAll() {
        return repository.findAll();
    }

    @Override
    public Podcast findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
    	logger.warn("Deleting podcast with id={}", id);
        repository.deleteById(id);
    }
}
