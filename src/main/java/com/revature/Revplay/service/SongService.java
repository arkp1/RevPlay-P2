
package com.revature.Revplay.service;

import java.util.List;

import com.revature.Revplay.dto.SongSearchDTO;
import com.revature.Revplay.entity.Song;

public interface SongService {
    Song save(Song obj);
    List<Song> findAll();
    Song findById(Long id);
    void deleteById(Long id);
    List<Song> getTopSongs();
    
    List<SongSearchDTO> search(String keyword);
}
