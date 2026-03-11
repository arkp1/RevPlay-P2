
package com.revature.Revplay.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;
import org.springframework.stereotype.Service;

import com.revature.Revplay.dto.SongSearchDTO;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.service.SongService;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository repository;
    private static final Logger logger = LogManager.getLogger(SongServiceImpl.class);

    public SongServiceImpl(SongRepository repository) {
        this.repository = repository;
    }

    @Override
    public Song save(Song obj) {
    	logger.debug("Saving song: title={}", obj.getTitle());
    	
        return repository.save(obj);
    }

    @Override
    public List<Song> findAll() {
        return repository.findAll();
    }

    @Override
    public Song findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public List<Song> getTopSongs() {
        return repository.findTop10ByOrderByPlayCountDesc();
    }
    
    @Override
    public List<SongSearchDTO> search(String keyword) {
        return repository.searchSongs(keyword);
    }
}

