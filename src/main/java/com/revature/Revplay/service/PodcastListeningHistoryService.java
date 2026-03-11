
package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.PodcastListeningHistory;

public interface PodcastListeningHistoryService {
    PodcastListeningHistory save(PodcastListeningHistory history);
    List<PodcastListeningHistory> findAll();
}
