package com.revature.Revplay.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.Revplay.entity.Podcast;
import com.revature.Revplay.repository.PodcastRepository;
import com.revature.Revplay.service.impl.PodcastServiceImpl;

/**
 * JUnit 4 tests for PodcastServiceImpl.
 */
public class PodcastServiceImplTest {

    private static final Logger logger = LogManager.getLogger(PodcastServiceImplTest.class);

    @Mock
    private PodcastRepository podcastRepository;

    @InjectMocks
    private PodcastServiceImpl podcastService;

    private Podcast testPodcast;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testPodcast = new Podcast();
        testPodcast.setPodcastId(1L);
        testPodcast.setTitle("Tech Talks");
        testPodcast.setDescription("A podcast about technology");

        logger.info("PodcastServiceImplTest setUp complete");
    }

    // ── save ──

    @Test
    public void testSave_ValidPodcast_ReturnsSavedPodcast() {
        logger.info("Running testSave_ValidPodcast_ReturnsSavedPodcast");

        when(podcastRepository.save(testPodcast)).thenReturn(testPodcast);

        Podcast result = podcastService.save(testPodcast);

        assertNotNull(result);
        assertEquals("Tech Talks", result.getTitle());
        verify(podcastRepository, times(1)).save(testPodcast);
    }

    // ── findAll ──

    @Test
    public void testFindAll_ReturnsAllPodcasts() {
        logger.info("Running testFindAll_ReturnsAllPodcasts");

        Podcast p2 = new Podcast();
        p2.setPodcastId(2L);
        p2.setTitle("Music History");

        when(podcastRepository.findAll()).thenReturn(Arrays.asList(testPodcast, p2));

        List<Podcast> result = podcastService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindAll_NoPodcasts_ReturnsEmptyList() {
        when(podcastRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(podcastService.findAll().isEmpty());
    }

    // ── findById ──

    @Test
    public void testFindById_ExistingPodcast_ReturnsPodcast() {
        logger.info("Running testFindById_ExistingPodcast_ReturnsPodcast");

        when(podcastRepository.findById(1L)).thenReturn(Optional.of(testPodcast));

        Podcast result = podcastService.findById(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getPodcastId());
        assertEquals("Tech Talks", result.getTitle());
    }

    @Test
    public void testFindById_NonExistentPodcast_ReturnsNull() {
        when(podcastRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(podcastService.findById(99L));
    }

    // ── deleteById ──

    @Test
    public void testDeleteById_CallsRepository() {
        logger.info("Running testDeleteById_CallsRepository");

        doNothing().when(podcastRepository).deleteById(1L);

        podcastService.deleteById(1L);

        verify(podcastRepository, times(1)).deleteById(1L);
    }
}