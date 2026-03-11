
package com.revature.Revplay.dto;

import java.time.LocalDateTime;

public class PlaylistFollowerDTO {

    private Long playlistId;
    private Long userId;
    private LocalDateTime followedAt;

    public PlaylistFollowerDTO() {}

    public Long getPlaylistId() { return playlistId; }
    public void setPlaylistId(Long playlistId) { this.playlistId = playlistId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getFollowedAt() { return followedAt; }
    public void setFollowedAt(LocalDateTime followedAt) { this.followedAt = followedAt; }
}
