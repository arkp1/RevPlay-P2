
package com.revature.Revplay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.Revplay.entity.PodcastFavorite;
import com.revature.Revplay.entity.compositekey.PodcastFavoriteId;

public interface PodcastFavoriteRepository 
        extends JpaRepository<PodcastFavorite, PodcastFavoriteId> {}
