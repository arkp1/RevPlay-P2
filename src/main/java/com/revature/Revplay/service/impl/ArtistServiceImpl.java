
package com.revature.Revplay.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.service.ArtistService;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository repository;
    private static final Logger logger = LogManager.getLogger(ArtistServiceImpl.class);

    public ArtistServiceImpl(ArtistRepository repository) {
        this.repository = repository;
    }

    @Override
    public Artist save(Artist obj) {
    	logger.info("Saving artist: name={}", obj.getArtistName());
        return repository.save(obj);
    }

    @Override
    public List<Artist> findAll() {
        return repository.findAll();
    }

    @Override
    public Artist findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
    	logger.warn("Deleting artist with id={}", id);
        repository.deleteById(id);
    }
}
