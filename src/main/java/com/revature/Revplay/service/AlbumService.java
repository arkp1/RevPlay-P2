
package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.Album;

public interface AlbumService {
    Album save(Album obj);
    List<Album> findAll();
    Album findById(Long id);
    void deleteById(Long id);
}
