package com.revature.Revplay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.Revplay.dto.ArtistAnalyticsDTO;
import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.ListeningHistoryRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.service.impl.ArtistAnalyticsServiceImpl;

/**
 * JUnit 4 tests for ArtistAnalyticsServiceImpl.
 */
public class ArtistAnalyticsServiceImplTest {

    private static final Logger logger = LogManager.getLogger(ArtistAnalyticsServiceImplTest.class);

    @Mock private ArtistRepository artistRepository;
    @Mock private SongRepository songRepository;
    @Mock private ListeningHistoryRepository listeningRepository;

    @InjectMocks
    private ArtistAnalyticsServiceImpl analyticsService;

    private Artist testArtist;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testArtist = new Artist();
        testArtist.setArtistId(1L);
        testArtist.setArtistName("Test Artist");
        testArtist.setFollowerCount(1000L);

        logger.info("ArtistAnalyticsServiceImplTest setUp complete");
    }

    // ── getAnalytics ──

    @Test
    public void testGetAnalytics_ValidArtist_ReturnsPopulatedDTO() {
        logger.info("Running testGetAnalytics_ValidArtist_ReturnsPopulatedDTO");

        when(artistRepository.findById(1L)).thenReturn(Optional.of(testArtist));
        when(songRepository.countByArtist_ArtistId(1L)).thenReturn(10L);
        when(songRepository.getTotalLikes(1L)).thenReturn(500L);
        when(listeningRepository.getTotalPlays(1L)).thenReturn(2000L);
        when(listeningRepository.getPlaysAfter(eq(1L), any())).thenReturn(150L);

        Song topSong = new Song();
        topSong.setSongId(1L);
        topSong.setTitle("Hit Song");
        topSong.setPlayCount(800L);
        when(songRepository.findTop5ByArtist_ArtistIdOrderByPlayCountDesc(1L))
                .thenReturn(Arrays.asList(topSong));

        ArtistAnalyticsDTO result = analyticsService.getAnalytics(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getArtistId());
        assertEquals("Test Artist", result.getArtistName());
        assertEquals(Long.valueOf(10L), result.getTotalSongs());
        assertEquals(Long.valueOf(2000L), result.getTotalPlays());
        assertEquals(Long.valueOf(500L), result.getTotalLikes());
        assertEquals(1, result.getTopSongs().size());
        assertEquals("Hit Song", result.getTopSongs().get(0).getTitle());

        logger.info("testGetAnalytics_ValidArtist_ReturnsPopulatedDTO PASSED");
    }

    @Test
    public void testGetAnalytics_ArtistWithNoSongs_ReturnsZeroCounts() {
        logger.info("Running testGetAnalytics_ArtistWithNoSongs_ReturnsZeroCounts");

        when(artistRepository.findById(1L)).thenReturn(Optional.of(testArtist));
        when(songRepository.countByArtist_ArtistId(1L)).thenReturn(0L);
        when(songRepository.getTotalLikes(1L)).thenReturn(0L);
        when(listeningRepository.getTotalPlays(1L)).thenReturn(0L);
        when(listeningRepository.getPlaysAfter(eq(1L), any())).thenReturn(0L);
        when(songRepository.findTop5ByArtist_ArtistIdOrderByPlayCountDesc(1L))
                .thenReturn(Collections.emptyList());

        ArtistAnalyticsDTO result = analyticsService.getAnalytics(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(0L), result.getTotalSongs());
        assertEquals(Long.valueOf(0L), result.getTotalPlays());
        assertTrue(result.getTopSongs().isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testGetAnalytics_ArtistNotFound_ThrowsException() {
        logger.info("Running testGetAnalytics_ArtistNotFound_ThrowsException");

        when(artistRepository.findById(99L)).thenReturn(Optional.empty());

        analyticsService.getAnalytics(99L);
    }

    @Test
    public void testGetAnalytics_FollowerCountIncluded() {
        logger.info("Running testGetAnalytics_FollowerCountIncluded");

        when(artistRepository.findById(1L)).thenReturn(Optional.of(testArtist));
        when(songRepository.countByArtist_ArtistId(1L)).thenReturn(5L);
        when(songRepository.getTotalLikes(1L)).thenReturn(100L);
        when(listeningRepository.getTotalPlays(1L)).thenReturn(500L);
        when(listeningRepository.getPlaysAfter(eq(1L), any())).thenReturn(50L);
        when(songRepository.findTop5ByArtist_ArtistIdOrderByPlayCountDesc(1L))
                .thenReturn(Collections.emptyList());

        ArtistAnalyticsDTO result = analyticsService.getAnalytics(1L);

        assertEquals(Long.valueOf(1000L), result.getFollowerCount());
    }
}