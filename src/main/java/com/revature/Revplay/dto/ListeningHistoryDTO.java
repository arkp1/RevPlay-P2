
package com.revature.Revplay.dto;

import java.time.LocalDateTime;

public class ListeningHistoryDTO {

    private Long historyId;
    private Long userId;
    private Long songId;
    private LocalDateTime playedAt;

    public ListeningHistoryDTO() {}

    public Long getHistoryId() { return historyId; }
    public void setHistoryId(Long historyId) { this.historyId = historyId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getSongId() { return songId; }
    public void setSongId(Long songId) { this.songId = songId; }

    public LocalDateTime getPlayedAt() { return playedAt; }
    public void setPlayedAt(LocalDateTime playedAt) { this.playedAt = playedAt; }
}
