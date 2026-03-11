
package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.Artist;

public interface ArtistService {
    Artist save(Artist obj);
    List<Artist> findAll();
    Artist findById(Long id);
    void deleteById(Long id);
}
