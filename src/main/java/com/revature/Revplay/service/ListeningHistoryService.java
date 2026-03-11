package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.ListeningHistory;

public interface ListeningHistoryService {

    ListeningHistory recordPlay(Long userId, Long songId);

    List<ListeningHistory> getRecentHistory(Long userId);

    List<ListeningHistory> getFullHistory(Long userId);
}
