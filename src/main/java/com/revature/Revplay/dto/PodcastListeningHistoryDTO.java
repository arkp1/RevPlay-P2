
package com.revature.Revplay.dto;

import java.time.LocalDateTime;

public class PodcastListeningHistoryDTO {

    private Long historyId;
    private Long userId;
    private Long episodeId;
    private LocalDateTime playedAt;

    public PodcastListeningHistoryDTO() {}

    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getEpisodeId() { return episodeId; }
    public void setEpisodeId(Long episodeId) { this.episodeId = episodeId; }

    public LocalDateTime getPlayedAt() { return playedAt; }
    public void setPlayedAt(LocalDateTime playedAt) { this.playedAt = playedAt; }
}
