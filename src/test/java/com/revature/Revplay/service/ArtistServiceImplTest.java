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

import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.service.impl.ArtistServiceImpl;


public class ArtistServiceImplTest {

    private static final Logger logger = LogManager.getLogger(ArtistServiceImplTest.class);

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistServiceImpl artistService;

    private Artist testArtist;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testArtist = new Artist();
        testArtist.setArtistId(1L);
        testArtist.setArtistName("The Weeknd");
        testArtist.setGenre("R&B");
        testArtist.setFollowerCount(1000L);

        logger.info("ArtistServiceImplTest setUp complete");
    }

    // ── save ──

    @Test
    public void testSave_ValidArtist_ReturnsSavedArtist() {
        logger.info("Running testSave_ValidArtist_ReturnsSavedArtist");

        when(artistRepository.save(testArtist)).thenReturn(testArtist);

        Artist result = artistService.save(testArtist);

        assertNotNull(result);
        assertEquals("The Weeknd", result.getArtistName());
        verify(artistRepository, times(1)).save(testArtist);
    }

    // ── findAll ──

    @Test
    public void testFindAll_ReturnsAllArtists() {
        logger.info("Running testFindAll_ReturnsAllArtists");

        Artist artist2 = new Artist();
        artist2.setArtistId(2L);
        artist2.setArtistName("Drake");

        when(artistRepository.findAll()).thenReturn(Arrays.asList(testArtist, artist2));

        List<Artist> result = artistService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindAll_NoArtists_ReturnsEmptyList() {
        when(artistRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(artistService.findAll().isEmpty());
    }

    // ── findById ──

    @Test
    public void testFindById_ExistingArtist_ReturnsArtist() {
        logger.info("Running testFindById_ExistingArtist_ReturnsArtist");

        when(artistRepository.findById(1L)).thenReturn(Optional.of(testArtist));

        Artist result = artistService.findById(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getArtistId());
        assertEquals("R&B", result.getGenre());
    }

    @Test
    public void testFindById_NonExistentArtist_ReturnsNull() {
        logger.info("Running testFindById_NonExistentArtist_ReturnsNull");

        when(artistRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(artistService.findById(99L));
    }

    // ── deleteById ──

    @Test
    public void testDeleteById_CallsRepository() {
        logger.info("Running testDeleteById_CallsRepository");

        doNothing().when(artistRepository).deleteById(1L);

        artistService.deleteById(1L);

        verify(artistRepository, times(1)).deleteById(1L);
    }
}