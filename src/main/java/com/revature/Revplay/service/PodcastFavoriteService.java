
package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.PodcastFavorite;

public interface PodcastFavoriteService {
    PodcastFavorite save(PodcastFavorite favorite);
    List<PodcastFavorite> findAll();
}
