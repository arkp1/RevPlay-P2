package com.revature.Revplay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.Revplay.entity.Favorite;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.UserRole;
import com.revature.Revplay.entity.compositekey.FavoriteId;
import com.revature.Revplay.repository.FavoriteRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.impl.FavoriteServiceImpl;

public class FavoriteServiceImplTest {

    private static final Logger logger = LogManager.getLogger(FavoriteServiceImplTest.class);

    @Mock private FavoriteRepository favoriteRepository;
    @Mock private UserRepository userRepository;
    @Mock private SongRepository songRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private User testUser;
    private Song testSong;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUserName("testuser");
        testUser.setRole(UserRole.LISTENER);

        testSong = new Song();
        testSong.setSongId(10L);
        testSong.setTitle("My Song");
        testSong.setLikeCount(0L);
    }

    @Test
    public void testAddFavorite_NewFavorite_IncreasesLikeCount() {
        when(favoriteRepository.existsByUser_UserIdAndSong_SongId(1L, 10L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(songRepository.findById(10L)).thenReturn(Optional.of(testSong));

        Favorite saved = new Favorite();
        saved.setId(new FavoriteId(1L, 10L));
        saved.setUser(testUser);
        saved.setSong(testSong);
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(saved);

        Favorite result = favoriteService.addFavorite(1L, 10L);

        assertNotNull(result);
        assertEquals(Long.valueOf(1L), testSong.getLikeCount());
        verify(songRepository, times(1)).save(testSong);
    }

    @Test(expected = RuntimeException.class)
    public void testAddFavorite_AlreadyFavorited_ThrowsException() {
        when(favoriteRepository.existsByUser_UserIdAndSong_SongId(1L, 10L)).thenReturn(true);
        favoriteService.addFavorite(1L, 10L);
    }

    @Test(expected = RuntimeException.class)
    public void testAddFavorite_UserNotFound_ThrowsException() {
        when(favoriteRepository.existsByUser_UserIdAndSong_SongId(1L, 10L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        favoriteService.addFavorite(1L, 10L);
    }

    @Test(expected = RuntimeException.class)
    public void testAddFavorite_SongNotFound_ThrowsException() {
        when(favoriteRepository.existsByUser_UserIdAndSong_SongId(1L, 99L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(songRepository.findById(99L)).thenReturn(Optional.empty());
        favoriteService.addFavorite(1L, 99L);
    }

    @Test
    public void testRemoveFavorite_ExistingFavorite_DecreasesLikeCount() {
        testSong.setLikeCount(5L);
        Favorite fav = new Favorite();
        fav.setId(new FavoriteId(1L, 10L));
        fav.setUser(testUser);
        fav.setSong(testSong);

        when(favoriteRepository.findById(new FavoriteId(1L, 10L))).thenReturn(Optional.of(fav));
        doNothing().when(favoriteRepository).deleteById(any());

        favoriteService.removeFavorite(1L, 10L);

        assertEquals(Long.valueOf(4L), testSong.getLikeCount());
        verify(favoriteRepository, times(1)).deleteById(any());
    }

    @Test(expected = RuntimeException.class)
    public void testRemoveFavorite_NotFavorited_ThrowsException() {
        when(favoriteRepository.findById(any())).thenReturn(Optional.empty());
        favoriteService.removeFavorite(1L, 10L);
    }

    @Test
    public void testGetUserFavorites_ReturnsFavoriteList() {
        Favorite f1 = new Favorite();
        Favorite f2 = new Favorite();
        when(favoriteRepository.findByUser_UserId(1L)).thenReturn(Arrays.asList(f1, f2));

        List<Favorite> result = favoriteService.getUserFavorites(1L);
        assertEquals(2, result.size());
    }
}