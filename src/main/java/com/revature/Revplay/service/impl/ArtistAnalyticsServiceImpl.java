package com.revature.Revplay.service.impl;

import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.Revplay.dto.*;
import com.revature.Revplay.entity.*;
import com.revature.Revplay.repository.*;
import com.revature.Revplay.service.ArtistAnalyticsService;

@Service
public class ArtistAnalyticsServiceImpl implements ArtistAnalyticsService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final ListeningHistoryRepository listeningRepository;
    private static final Logger logger = LogManager.getLogger(ArtistAnalyticsServiceImpl.class);

    public ArtistAnalyticsServiceImpl(ArtistRepository artistRepository,
                                      SongRepository songRepository,
                                      ListeningHistoryRepository listeningRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.listeningRepository = listeningRepository;
    }

    @Override
    public ArtistAnalyticsDTO getAnalytics(Long artistId) {
    	logger.info("Fetching analytics for artistId={}", artistId);

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        Long totalSongs = songRepository.countByArtist_ArtistId(artistId);
        Long totalLikes = songRepository.getTotalLikes(artistId);
        Long totalPlays = listeningRepository.getTotalPlays(artistId);

        LocalDateTime now = LocalDateTime.now();
        Long plays7 = listeningRepository.getPlaysAfter(artistId, now.minusDays(7));
        Long plays30 = listeningRepository.getPlaysAfter(artistId, now.minusDays(30));

        List<TopSongDTO> topSongs = songRepository
                .findTop5ByArtist_ArtistIdOrderByPlayCountDesc(artistId)
                .stream()
                .map(s -> new TopSongDTO(s.getSongId(), s.getTitle(), s.getPlayCount()))
                .collect(Collectors.toList());

        return new ArtistAnalyticsDTO(
                artist.getArtistId(),
                artist.getArtistName(),
                artist.getFollowerCount(),
                totalSongs,
                totalPlays,
                totalLikes,
                plays7,
                plays30,
                topSongs
        );
    }
}
