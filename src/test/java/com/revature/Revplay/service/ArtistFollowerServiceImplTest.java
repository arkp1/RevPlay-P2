package com.revature.Revplay.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.entity.ArtistFollower;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.UserRole;
import com.revature.Revplay.repository.ArtistFollowerRepository;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.impl.ArtistFollowerServiceImpl;

import java.util.Optional;

/**
 * JUnit 4 tests for ArtistFollowerServiceImpl.
 */
public class ArtistFollowerServiceImplTest {

    private static final Logger logger = LogManager.getLogger(ArtistFollowerServiceImplTest.class);

    @Mock private ArtistFollowerRepository repository;
    @Mock private UserRepository userRepository;
    @Mock private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistFollowerServiceImpl artistFollowerService;

    private User testUser;
    private Artist testArtist;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setUserId(1L);
        testUser.setUserName("fan_user");
        testUser.setRole(UserRole.LISTENER);

        testArtist = new Artist();
        testArtist.setArtistId(10L);
        testArtist.setArtistName("The Weeknd");
        testArtist.setFollowerCount(500L);

        logger.info("ArtistFollowerServiceImplTest setUp complete");
    }

    // ── follow ──

    @Test
    public void testFollow_NewFollow_IncreasesFollowerCount() {
        logger.info("Running testFollow_NewFollow_IncreasesFollowerCount");

        when(repository.existsByUser_UserIdAndArtist_ArtistId(1L, 10L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(artistRepository.findById(10L)).thenReturn(Optional.of(testArtist));
        when(repository.save(any(ArtistFollower.class))).thenReturn(new ArtistFollower());
        when(artistRepository.save(any(Artist.class))).thenReturn(testArtist);

        ArtistFollower result = artistFollowerService.follow(1L, 10L);

        assertNotNull(result);
        assertEquals(Long.valueOf(501L), testArtist.getFollowerCount());
        verify(repository, times(1)).save(any(ArtistFollower.class));
        verify(artistRepository, times(1)).save(testArtist);

        logger.info("testFollow_NewFollow_IncreasesFollowerCount PASSED");
    }

    @Test(expected = RuntimeException.class)
    public void testFollow_AlreadyFollowing_ThrowsException() {
        logger.info("Running testFollow_AlreadyFollowing_ThrowsException");

        when(repository.existsByUser_UserIdAndArtist_ArtistId(1L, 10L)).thenReturn(true);

        artistFollowerService.follow(1L, 10L);
    }

    @Test(expected = RuntimeException.class)
    public void testFollow_UserNotFound_ThrowsException() {
        when(repository.existsByUser_UserIdAndArtist_ArtistId(1L, 10L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        artistFollowerService.follow(1L, 10L);
    }

    @Test(expected = RuntimeException.class)
    public void testFollow_ArtistNotFound_ThrowsException() {
        when(repository.existsByUser_UserIdAndArtist_ArtistId(1L, 10L)).thenReturn(false);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(artistRepository.findById(10L)).thenReturn(Optional.empty());

        artistFollowerService.follow(1L, 10L);
    }

    // ── unfollow ──

    @Test
    public void testUnfollow_ExistingFollow_DecreasesFollowerCount() {
        logger.info("Running testUnfollow_ExistingFollow_DecreasesFollowerCount");

        when(repository.existsByUser_UserIdAndArtist_ArtistId(1L, 10L)).thenReturn(true);
        when(artistRepository.findById(10L)).thenReturn(Optional.of(testArtist));
        doNothing().when(repository).deleteByUser_UserIdAndArtist_ArtistId(1L, 10L);
        when(artistRepository.save(any())).thenReturn(testArtist);

        artistFollowerService.unfollow(1L, 10L);

        assertEquals(Long.valueOf(499L), testArtist.getFollowerCount());
        verify(repository, times(1)).deleteByUser_UserIdAndArtist_ArtistId(1L, 10L);
    }

    @Test
    public void testUnfollow_NotFollowing_DoesNothing() {
        logger.info("Running testUnfollow_NotFollowing_DoesNothing");

        when(repository.existsByUser_UserIdAndArtist_ArtistId(1L, 10L)).thenReturn(false);

        artistFollowerService.unfollow(1L, 10L);

        verify(repository, never()).deleteByUser_UserIdAndArtist_ArtistId(anyLong(), anyLong());
    }

    // ── isFollowing ──

    @Test
    public void testIsFollowing_WhenFollowing_ReturnsTrue() {
        when(repository.existsByUser_UserIdAndArtist_ArtistId(1L, 10L)).thenReturn(true);

        assertTrue(artistFollowerService.isFollowing(1L, 10L));
    }

    @Test
    public void testIsFollowing_WhenNotFollowing_ReturnsFalse() {
        when(repository.existsByUser_UserIdAndArtist_ArtistId(1L, 10L)).thenReturn(false);

        assertFalse(artistFollowerService.isFollowing(1L, 10L));
    }

    // ── getFollowerCount ──

    @Test
    public void testGetFollowerCount_ReturnsCorrectCount() {
        when(repository.countByArtist_ArtistId(10L)).thenReturn(250L);

        assertEquals(250L, artistFollowerService.getFollowerCount(10L));
    }

    // ── getArtistFollowers / getUserFollowing ──

    @Test
    public void testGetArtistFollowers_ReturnsFollowerList() {
        ArtistFollower af1 = new ArtistFollower();
        ArtistFollower af2 = new ArtistFollower();
        when(repository.findByArtist_ArtistId(10L)).thenReturn(Arrays.asList(af1, af2));

        List<ArtistFollower> result = artistFollowerService.getArtistFollowers(10L);

        assertEquals(2, result.size());
    }

    @Test
    public void testGetUserFollowing_ReturnsFollowingList() {
        ArtistFollower af1 = new ArtistFollower();
        when(repository.findByUser_UserId(1L)).thenReturn(Arrays.asList(af1));

        List<ArtistFollower> result = artistFollowerService.getUserFollowing(1L);

        assertEquals(1, result.size());
    }
}