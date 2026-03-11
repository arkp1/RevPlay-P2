
package com.revature.Revplay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.Revplay.dto.AlbumSearchDTO;
import com.revature.Revplay.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

		@Query("""
		    SELECT new com.revature.Revplay.dto.AlbumSearchDTO(
		        a.albumId,
		        a.title
		    )
		    FROM Album a
		    WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
		""")
		List<AlbumSearchDTO> searchAlbums(@Param("keyword") String keyword);
		List<Album> findTop10ByOrderByReleaseDateDesc();
		List<Album> findByArtist_User_UserId(Long userId);
}
