package com.revature.Revplay.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.Revplay.entity.ListeningHistory;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.entity.User;
import com.revature.Revplay.repository.ListeningHistoryRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.repository.UserRepository;
import com.revature.Revplay.service.ListeningHistoryService;

@Service
public class ListeningHistoryServiceImpl implements ListeningHistoryService {

    private final ListeningHistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private static final Logger logger = LogManager.getLogger(ListeningHistoryServiceImpl.class);

    public ListeningHistoryServiceImpl(ListeningHistoryRepository historyRepository,
                                       UserRepository userRepository,
                                       SongRepository songRepository) {
        this.historyRepository = historyRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @Transactional
    @Override
    public ListeningHistory recordPlay(Long userId, Long songId) {
    	logger.info("Recording play: userId={}, songId={}", userId, songId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        if (song.getPlayCount() == null) {
            song.setPlayCount(0L);
        }

        song.setPlayCount(song.getPlayCount() + 1);
        songRepository.save(song);

        ListeningHistory history = new ListeningHistory();
        history.setUser(user);
        history.setSong(song);

        return historyRepository.save(history);
    }

    @Override
    public List<ListeningHistory> getRecentHistory(Long userId) {
        return historyRepository.findTop50ByUser_UserIdOrderByPlayedAtDesc(userId);
    }

    @Override
    public List<ListeningHistory> getFullHistory(Long userId) {
        return historyRepository.findByUser_UserIdOrderByPlayedAtDesc(userId);
    }
}
