package com.revature.Revplay.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.Revplay.dto.AlbumSearchDTO;
import com.revature.Revplay.dto.ArtistSearchDTO;
import com.revature.Revplay.dto.GlobalSearchDTO;
import com.revature.Revplay.dto.PlaylistSearchDTO;
import com.revature.Revplay.dto.SongSearchDTO;
import com.revature.Revplay.repository.AlbumRepository;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.PlaylistRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.service.impl.SearchServiceImpl;

/**
 * JUnit 4 tests for SearchServiceImpl.
 */
public class SearchServiceImplTest {

    private static final Logger logger = LogManager.getLogger(SearchServiceImplTest.class);

    @Mock private SongRepository songRepository;
    @Mock private ArtistRepository artistRepository;
    @Mock private AlbumRepository albumRepository;
    @Mock private PlaylistRepository playlistRepository;

    @InjectMocks
    private SearchServiceImpl searchService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("SearchServiceImplTest setUp complete");
    }

    // ── search ──

    @Test
    public void testSearch_WithResults_ReturnsPopulatedGlobalSearchDTO() {
        logger.info("Running testSearch_WithResults_ReturnsPopulatedGlobalSearchDTO");

        SongSearchDTO song = mock(SongSearchDTO.class);
        ArtistSearchDTO artist = mock(ArtistSearchDTO.class);
        AlbumSearchDTO album = mock(AlbumSearchDTO.class);
        PlaylistSearchDTO playlist = mock(PlaylistSearchDTO.class);

        when(songRepository.searchSongs("pop")).thenReturn(Arrays.asList(song));
        when(artistRepository.searchArtists("pop")).thenReturn(Arrays.asList(artist));
        when(albumRepository.searchAlbums("pop")).thenReturn(Arrays.asList(album));
        when(playlistRepository.searchPlaylists("pop")).thenReturn(Arrays.asList(playlist));

        GlobalSearchDTO result = searchService.search("pop");

        assertNotNull(result);
        assertEquals(1, result.getSongs().size());
        assertEquals(1, result.getArtists().size());
        assertEquals(1, result.getAlbums().size());
        assertEquals(1, result.getPlaylists().size());

        logger.info("testSearch_WithResults_ReturnsPopulatedGlobalSearchDTO PASSED");
    }

    @Test
    public void testSearch_NoResults_ReturnsEmptyGlobalSearchDTO() {
        logger.info("Running testSearch_NoResults_ReturnsEmptyGlobalSearchDTO");

        when(songRepository.searchSongs("zzz")).thenReturn(Collections.emptyList());
        when(artistRepository.searchArtists("zzz")).thenReturn(Collections.emptyList());
        when(albumRepository.searchAlbums("zzz")).thenReturn(Collections.emptyList());
        when(playlistRepository.searchPlaylists("zzz")).thenReturn(Collections.emptyList());

        GlobalSearchDTO result = searchService.search("zzz");

        assertNotNull(result);
        assertTrue(result.getSongs().isEmpty());
        assertTrue(result.getArtists().isEmpty());
        assertTrue(result.getAlbums().isEmpty());
        assertTrue(result.getPlaylists().isEmpty());
    }

    @Test
    public void testSearch_SongsFoundButNoArtists_ReturnsPartialResults() {
        logger.info("Running testSearch_SongsFoundButNoArtists_ReturnsPartialResults");

        SongSearchDTO song = mock(SongSearchDTO.class);

        when(songRepository.searchSongs("rock")).thenReturn(Arrays.asList(song, song));
        when(artistRepository.searchArtists("rock")).thenReturn(Collections.emptyList());
        when(albumRepository.searchAlbums("rock")).thenReturn(Collections.emptyList());
        when(playlistRepository.searchPlaylists("rock")).thenReturn(Collections.emptyList());

        GlobalSearchDTO result = searchService.search("rock");

        assertEquals(2, result.getSongs().size());
        assertTrue(result.getArtists().isEmpty());
    }

    @Test
    public void testSearch_AllRepositoriesAreCalled() {
        logger.info("Running testSearch_AllRepositoriesAreCalled");

        when(songRepository.searchSongs("jazz")).thenReturn(Collections.emptyList());
        when(artistRepository.searchArtists("jazz")).thenReturn(Collections.emptyList());
        when(albumRepository.searchAlbums("jazz")).thenReturn(Collections.emptyList());
        when(playlistRepository.searchPlaylists("jazz")).thenReturn(Collections.emptyList());

        searchService.search("jazz");

        // Verify all 4 repositories were queried
        verify(songRepository, times(1)).searchSongs("jazz");
        verify(artistRepository, times(1)).searchArtists("jazz");
        verify(albumRepository, times(1)).searchAlbums("jazz");
        verify(playlistRepository, times(1)).searchPlaylists("jazz");
    }
}