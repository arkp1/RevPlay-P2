
package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.Podcast;

public interface PodcastService {
    Podcast save(Podcast obj);
    List<Podcast> findAll();
    Podcast findById(Long id);
    void deleteById(Long id);
}
