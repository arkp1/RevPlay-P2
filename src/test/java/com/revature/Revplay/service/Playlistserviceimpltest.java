package com.revature.Revplay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
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

import com.revature.Revplay.entity.Playlist;
import com.revature.Revplay.entity.PlaylistPrivacy;
import com.revature.Revplay.entity.PlaylistSong;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.UserRole;
import com.revature.Revplay.entity.compositekey.PlaylistSongId;
import com.revature.Revplay.repository.PlaylistRepository;
import com.revature.Revplay.repository.PlaylistSongRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.impl.PlaylistServiceImpl;

public class Playlistserviceimpltest {

    private static final Logger logger = LogManager.getLogger(AuthServiceImplTest.class);

    @Mock private PlaylistRepository playlistRepository;
    @Mock private PlaylistSongRepository playlistSongRepository;
    @Mock private SongRepository songRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private PlaylistServiceImpl playlistService;

    private User testUser;
    private Playlist testPlaylist;
    private Song testSong;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setUserId(1L);
        testUser.setRole(UserRole.LISTENER);

        testPlaylist = new Playlist();
        testPlaylist.setPlaylistId(1L);
        testPlaylist.setName("My Playlist");
        testPlaylist.setPrivacy(PlaylistPrivacy.PUBLIC);
        testPlaylist.setUser(testUser);

        testSong = new Song();
        testSong.setSongId(10L);
        testSong.setTitle("Banger Track");
    }

    @Test
    public void testSave_ValidPlaylist_ReturnsSavedPlaylist() {
        when(playlistRepository.save(testPlaylist)).thenReturn(testPlaylist);

        Playlist result = playlistService.save(testPlaylist);

        assertNotNull(result);
        assertEquals("My Playlist", result.getName());
    }

    @Test
    public void testFindAll_ReturnsAllPlaylists() {
        when(playlistRepository.findAll()).thenReturn(Arrays.asList(testPlaylist, new Playlist()));

        assertEquals(2, playlistService.findAll().size());
    }

    @Test
    public void testFindById_ExistingPlaylist_ReturnsPlaylist() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(testPlaylist));

        Playlist result = playlistService.findById(1L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getPlaylistId());
    }

    @Test(expected = RuntimeException.class)
    public void testFindById_NotFound_ThrowsException() {
        when(playlistRepository.findById(99L)).thenReturn(Optional.empty());
        playlistService.findById(99L);
    }

    @Test
    public void testDeleteById_CallsRepository() {
        doNothing().when(playlistRepository).deleteById(1L);
        playlistService.deleteById(1L);
        verify(playlistRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAddSong_ValidPlaylistAndSong_ReturnsPlaylistSong() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(testPlaylist));
        when(songRepository.findById(10L)).thenReturn(Optional.of(testSong));
        when(playlistSongRepository.countByPlaylist_PlaylistId(1L)).thenReturn(2);

        PlaylistSong saved = new PlaylistSong();
        saved.setId(new PlaylistSongId(1L, 10L));
        saved.setPlaylist(testPlaylist);
        saved.setSong(testSong);
        saved.setPosition(3);
        when(playlistSongRepository.save(any(PlaylistSong.class))).thenReturn(saved);

        PlaylistSong result = playlistService.addSong(1L, 10L);

        assertNotNull(result);
        assertEquals(Integer.valueOf(3), result.getPosition());
    }

    @Test(expected = RuntimeException.class)
    public void testAddSong_SongNotFound_ThrowsException() {
        when(playlistRepository.findById(1L)).thenReturn(Optional.of(testPlaylist));
        when(songRepository.findById(99L)).thenReturn(Optional.empty());
        playlistService.addSong(1L, 99L);
    }

    @Test
    public void testGetPlaylistSongs_ReturnsOrderedList() {
        PlaylistSong ps1 = new PlaylistSong(); ps1.setPosition(1);
        PlaylistSong ps2 = new PlaylistSong(); ps2.setPosition(2);
        when(playlistSongRepository.findByPlaylist_PlaylistIdOrderByPositionAsc(1L))
                .thenReturn(Arrays.asList(ps1, ps2));

        List<PlaylistSong> result = playlistService.getPlaylistSongs(1L);

        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(1), result.get(0).getPosition());
    }

    @Test
    public void testGetPublicPlaylists_ReturnsOnlyPublicPlaylists() {
        when(playlistRepository.findByPrivacy(PlaylistPrivacy.PUBLIC))
                .thenReturn(Collections.singletonList(testPlaylist));

        List<Playlist> result = playlistService.getPublicPlaylists();

        assertEquals(1, result.size());
        assertEquals(PlaylistPrivacy.PUBLIC, result.get(0).getPrivacy());
    }
}