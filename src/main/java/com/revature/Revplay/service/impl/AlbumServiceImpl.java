
package com.revature.Revplay.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.revature.Revplay.entity.Album;
import com.revature.Revplay.repository.AlbumRepository;
import com.revature.Revplay.service.AlbumService;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository repository;

    public AlbumServiceImpl(AlbumRepository repository) {
        this.repository = repository;
    }

    @Override
    public Album save(Album obj) {
        return repository.save(obj);
    }

    @Override
    public List<Album> findAll() {
        return repository.findAll();
    }

    @Override
    public Album findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
