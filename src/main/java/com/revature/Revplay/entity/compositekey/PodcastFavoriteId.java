
package com.revature.Revplay.entity.compositekey;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PodcastFavoriteId implements Serializable {

    private Long userId;
    private Long episodeId;

    public PodcastFavoriteId() {}

    public PodcastFavoriteId(Long userId, Long episodeId) {
        this.userId = userId;
        this.episodeId = episodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PodcastFavoriteId)) return false;
        PodcastFavoriteId that = (PodcastFavoriteId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(episodeId, that.episodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, episodeId);
    }
}
