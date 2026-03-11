package com.revature.Revplay.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.Revplay.entity.ListeningHistory;

public interface ListeningHistoryRepository extends JpaRepository<ListeningHistory, Long> {

    List<ListeningHistory> findTop50ByUser_UserIdOrderByPlayedAtDesc(Long userId);

    List<ListeningHistory> findByUser_UserIdOrderByPlayedAtDesc(Long userId);
    
    
    
    @Query("""
    SELECT COUNT(lh)
    FROM ListeningHistory lh
    WHERE lh.song.artist.artistId = :artistId
    """)
    Long getTotalPlays(@Param("artistId") Long artistId);

    @Query("""
    SELECT COUNT(lh)
    FROM ListeningHistory lh
    WHERE lh.song.artist.artistId = :artistId
    AND lh.playedAt >= :date
    """)
    Long getPlaysAfter(@Param("artistId") Long artistId,
                       @Param("date") LocalDateTime date);
}
