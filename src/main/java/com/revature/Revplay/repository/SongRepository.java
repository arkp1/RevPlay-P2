
package com.revature.Revplay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.Revplay.dto.SongSearchDTO;
import com.revature.Revplay.entity.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
	List<Song> findTop10ByOrderByPlayCountDesc();
	
	@Query("""
		    SELECT new com.revature.Revplay.dto.SongSearchDTO(
		        s.songId,
		        s.title,
		        s.artist.artistName,
		        CASE WHEN s.album IS NOT NULL THEN s.album.title ELSE null END,
		        s.genre,
		        s.duration,
		        CASE WHEN s.album IS NOT NULL THEN s.album.coverImage ELSE null END
		    )
		    FROM Song s
		    WHERE LOWER(s.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
		       OR LOWER(s.genre) LIKE LOWER(CONCAT('%', :keyword, '%'))
		       OR LOWER(s.artist.artistName) LIKE LOWER(CONCAT('%', :keyword, '%'))
		""")
		List<SongSearchDTO> searchSongs(@Param("keyword") String keyword);
	
	
	long countByArtist_ArtistId(Long artistId);

	@org.springframework.data.jpa.repository.Query("""
	SELECT COALESCE(SUM(s.likeCount),0)
	FROM Song s
	WHERE s.artist.artistId = :artistId
	""")
	Long getTotalLikes(@org.springframework.data.repository.query.Param("artistId") Long artistId);

	java.util.List<Song> findTop5ByArtist_ArtistIdOrderByPlayCountDesc(Long artistId);
	
	List<Song> findByArtist_ArtistId(Long artistId);

}
