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

import com.revature.Revplay.entity.ListeningHistory;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.UserRole;
import com.revature.Revplay.repository.ListeningHistoryRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.impl.ListeningHistoryServiceImpl;

public class ListeningHistoryServiceImplTest {

    private static final Logger logger = LogManager.getLogger(ListeningHistoryServiceImplTest.class);

    @Mock private ListeningHistoryRepository historyRepository;
    @Mock private UserRepository userRepository;
    @Mock private SongRepository songRepository;

    @InjectMocks
    private ListeningHistoryServiceImpl historyService;

    private User testUser;
    private Song testSong;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setUserId(1L);
        testUser.setRole(UserRole.LISTENER);

        testSong = new Song();
        testSong.setSongId(5L);
        testSong.setPlayCount(10L);
    }

    @Test
    public void testRecordPlay_ValidIds_IncrementsPlayCountAndSavesHistory() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(songRepository.findById(5L)).thenReturn(Optional.of(testSong));
        when(historyRepository.save(any(ListeningHistory.class))).thenReturn(new ListeningHistory());

        ListeningHistory result = historyService.recordPlay(1L, 5L);

        assertNotNull(result);
        assertEquals(Long.valueOf(11L), testSong.getPlayCount());
        verify(songRepository, times(1)).save(testSong);
        verify(historyRepository, times(1)).save(any());
    }

    @Test
    public void testRecordPlay_SongPlayCountNull_InitializesToOneAfterPlay() {
        testSong.setPlayCount(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(songRepository.findById(5L)).thenReturn(Optional.of(testSong));
        when(historyRepository.save(any())).thenReturn(new ListeningHistory());

        historyService.recordPlay(1L, 5L);

        assertEquals(Long.valueOf(1L), testSong.getPlayCount());
    }

    @Test(expected = RuntimeException.class)
    public void testRecordPlay_UserNotFound_ThrowsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        historyService.recordPlay(99L, 5L);
    }

    @Test(expected = RuntimeException.class)
    public void testRecordPlay_SongNotFound_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(songRepository.findById(99L)).thenReturn(Optional.empty());
        historyService.recordPlay(1L, 99L);
    }

    @Test
    public void testGetRecentHistory_ReturnsRecords() {
        when(historyRepository.findTop50ByUser_UserIdOrderByPlayedAtDesc(1L))
                .thenReturn(Arrays.asList(new ListeningHistory(), new ListeningHistory()));

        assertEquals(2, historyService.getRecentHistory(1L).size());
    }

    @Test
    public void testGetRecentHistory_NoHistory_ReturnsEmptyList() {
        when(historyRepository.findTop50ByUser_UserIdOrderByPlayedAtDesc(1L))
                .thenReturn(Collections.emptyList());

        assertTrue(historyService.getRecentHistory(1L).isEmpty());
    }

    @Test
    public void testGetFullHistory_ReturnsAllRecords() {
        when(historyRepository.findByUser_UserIdOrderByPlayedAtDesc(1L))
                .thenReturn(Collections.singletonList(new ListeningHistory()));

        assertEquals(1, historyService.getFullHistory(1L).size());
    }
}