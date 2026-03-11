
package com.revature.Revplay.dto;

import java.time.LocalDateTime;

public class PodcastFavoriteDTO {

    private Long userId;
    private Long episodeId;
    private LocalDateTime favoritedAt;

    public PodcastFavoriteDTO() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getEpisodeId() { return episodeId; }
    public void setEpisodeId(Long episodeId) { this.episodeId = episodeId; }

    public LocalDateTime getFavoritedAt() { return favoritedAt; }
    public void setFavoritedAt(LocalDateTime favoritedAt) { this.favoritedAt = favoritedAt; }
}
