package com.revature.Revplay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.Revplay.dto.PlaylistSearchDTO;
import com.revature.Revplay.entity.Playlist;
import com.revature.Revplay.entity.PlaylistPrivacy;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUser_UserId(Long userId);

    List<Playlist> findByUser_UserIdAndPrivacy(Long userId, PlaylistPrivacy privacy);

    List<Playlist> findByPrivacy(PlaylistPrivacy privacy);
    
    
    @Query("""
    	    SELECT new com.revature.Revplay.dto.PlaylistSearchDTO(
    	        p.playlistId,
    	        p.name
    	    )
    	    FROM Playlist p
    	    WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    	""")
    
    	List<PlaylistSearchDTO> searchPlaylists(@Param("keyword") String keyword);
    
    	List<Playlist> findByUserUserId(Long userId);
}