
package com.revature.Revplay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.Revplay.dto.ArtistSearchDTO;
import com.revature.Revplay.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
	@Query("""
		    SELECT new com.revature.Revplay.dto.ArtistSearchDTO(
		        a.artistId,
		        a.artistName,
		        a.bannerImage
		    )
		    FROM Artist a
		    WHERE LOWER(a.artistName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		""")
		List<ArtistSearchDTO> searchArtists(@Param("keyword") String keyword);
		List<Artist> findTop10ByOrderByFollowerCountDesc();
		Optional<Artist> findByUser_UserId(Long userId);
}

