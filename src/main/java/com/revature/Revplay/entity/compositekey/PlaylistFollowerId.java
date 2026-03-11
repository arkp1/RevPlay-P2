
package com.revature.Revplay.entity.compositekey;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlaylistFollowerId implements Serializable {

    private Long playlistId;
    private Long userId;

    public PlaylistFollowerId() {}

    public PlaylistFollowerId(Long playlistId, Long userId) {
        this.playlistId = playlistId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistFollowerId)) return false;
        PlaylistFollowerId that = (PlaylistFollowerId) o;
        return Objects.equals(playlistId, that.playlistId) &&
               Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId, userId);
    }
}
