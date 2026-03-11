package com.revature.Revplay.entity.compositekey;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FavoriteId implements Serializable {

	@Column(name = "user_id")
    private Long userId;
	
	@Column(name = "song_id")
    private Long songId;

    public FavoriteId() {}

    public FavoriteId(Long userId, Long songId) {
        this.userId = userId;
        this.songId = songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoriteId)) return false;
        FavoriteId that = (FavoriteId) o;
        return Objects.equals(userId, that.userId)
            && Objects.equals(songId, that.songId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, songId);
    }
}