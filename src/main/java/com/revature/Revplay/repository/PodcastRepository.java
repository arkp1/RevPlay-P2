
package com.revature.Revplay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.Revplay.entity.Podcast;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {

	List<Podcast> findTop10ByOrderByCreatedAtDesc();
	
	List<Podcast> findByArtist_ArtistId(Long artistId);
}
