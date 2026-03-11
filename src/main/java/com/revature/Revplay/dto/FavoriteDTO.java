
package com.revature.Revplay.dto;

import java.time.LocalDateTime;

public class FavoriteDTO {

    private Long userId;
    private Long songId;
    private LocalDateTime addedAt;

    public FavoriteDTO() {}

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getSongId() { return songId; }
    public void setSongId(Long songId) { this.songId = songId; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
}
