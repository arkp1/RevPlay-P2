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

import com.revature.Revplay.entity.Album;
import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.repository.AlbumRepository;
import com.revature.Revplay.service.impl.AlbumServiceImpl;

public class Albumserviceimpltest {

    private static final Logger logger = LogManager.getLogger(AlbumServiceImpl.class);

    @Mock private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumServiceImpl albumService;

    private Album testAlbum;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Artist artist = new Artist();
        artist.setArtistId(1L);
        artist.setArtistName("Test Artist");

        testAlbum = new Album();
        testAlbum.setAlbumId(1L);
        testAlbum.setTitle("Greatest Hits");
        testAlbum.setArtist(artist);
    }

    @Test
    public void testSave_ValidAlbum_ReturnsSavedAlbum() {
        when(albumRepository.save(testAlbum)).thenReturn(testAlbum);

        Album result = albumService.save(testAlbum);

        assertNotNull(result);
        assertEquals("Greatest Hits", result.getTitle());
        verify(albumRepository, times(1)).save(testAlbum);
    }

    @Test
    public void testFindAll_MultipleAlbums_ReturnsAll() {
    	Album album2 = new Album(); album2.setTitle("Second Album");
        when(albumRepository.findAll()).thenReturn(Arrays.asList(testAlbum, album2));

        assertEquals(2, albumService.findAll().size());
    }

    @Test
    public void testFindAll_NoAlbums_ReturnsEmptyList() {
        when(albumRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(albumService.findAll().isEmpty());
    }

    @Test
    public void testFindById_ExistingAlbum_ReturnsAlbum() {
        when(albumRepository.findById(1L)).thenReturn(Optional.of(testAlbum));

        Album result = albumService.findById(1L);

        assertNotNull(result);
        assertEquals("Greatest Hits", result.getTitle());
    }

    @Test
    public void testFindById_NonExistentAlbum_ReturnsNull() {
        when(albumRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(albumService.findById(99L));
    }

    @Test
    public void testDeleteById_ValidId_CallsRepositoryDelete() {
        doNothing().when(albumRepository).deleteById(1L);

        albumService.deleteById(1L);

        verify(albumRepository, times(1)).deleteById(1L);
    }
}