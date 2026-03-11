
package com.revature.Revplay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.Revplay.entity.PodcastEpisode;

public interface PodcastEpisodeRepository extends JpaRepository<PodcastEpisode, Long> {
}
