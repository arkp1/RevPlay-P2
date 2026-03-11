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

import com.revature.Revplay.dto.SongSearchDTO;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.service.impl.SongServiceImpl;

public class SongServiceImplTest {

    private static final Logger logger = LogManager.getLogger(SongServiceImplTest.class);

    @Mock private SongRepository songRepository;

    @InjectMocks
    private SongServiceImpl songService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        logger.info("SongServiceImplTest setUp complete");
    }

    @Test
    public void testSave_ValidSong_ReturnsSavedSong() {
        Song song = new Song();
        song.setTitle("Test Song");
        when(songRepository.save(song)).thenReturn(song);

        Song result = songService.save(song);

        assertNotNull(result);
        assertEquals("Test Song", result.getTitle());
        verify(songRepository, times(1)).save(song);
    }

    @Test
    public void testFindAll_ReturnsListOfSongs() {
        Song s1 = new Song(); s1.setTitle("Song A");
        Song s2 = new Song(); s2.setTitle("Song B");
        when(songRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Song> result = songService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindAll_EmptyLibrary_ReturnsEmptyList() {
        when(songRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(songService.findAll().isEmpty());
    }

    @Test
    public void testFindById_ExistingSong_ReturnsSong() {
        Song song = new Song();
        song.setSongId(1L);
        when(songRepository.findById(1L)).thenReturn(Optional.of(song));

        Song result = songService.findById(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getSongId());
    }

    @Test
    public void testFindById_NonExistentSong_ReturnsNull() {
        when(songRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(songService.findById(99L));
    }

    @Test
    public void testDeleteById_CallsRepositoryDelete() {
        doNothing().when(songRepository).deleteById(1L);

        songService.deleteById(1L);

        verify(songRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetTopSongs_ReturnsTop10Songs() {
        Song s1 = new Song(); s1.setPlayCount(500L);
        Song s2 = new Song(); s2.setPlayCount(300L);
        when(songRepository.findTop10ByOrderByPlayCountDesc()).thenReturn(Arrays.asList(s1, s2));

        List<Song> result = songService.getTopSongs();

        assertEquals(2, result.size());
        assertEquals(Long.valueOf(500L), result.get(0).getPlayCount());
    }

    @Test
    public void testSearch_WithKeyword_ReturnsDTOList() {
        SongSearchDTO dto = mock(SongSearchDTO.class);
        when(songRepository.searchSongs("pop")).thenReturn(Arrays.asList(dto));

        assertEquals(1, songService.search("pop").size());
    }

    @Test
    public void testSearch_NoMatch_ReturnsEmptyList() {
        when(songRepository.searchSongs("zzz")).thenReturn(Collections.emptyList());

        assertTrue(songService.search("zzz").isEmpty());
    }
}