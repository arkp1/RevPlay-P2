
package com.revature.Revplay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.Revplay.entity.PodcastListeningHistory;

public interface PodcastListeningHistoryRepository 
        extends JpaRepository<PodcastListeningHistory, Long> {}
