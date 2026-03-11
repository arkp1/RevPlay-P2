package com.revature.Revplay.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.revature.Revplay.entity.compositekey.FavoriteId;

@Entity
@Table(name = "favorites")
public class Favorite {

    @EmbeddedId
    private FavoriteId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "added_at", insertable = false, updatable = false)
    private LocalDateTime addedAt;

    public Favorite() {}

	public FavoriteId getId() {
		return id;
	}

	public void setId(FavoriteId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}

	public Favorite(FavoriteId id, User user, Song song, LocalDateTime addedAt) {
		this.id = id;
		this.user = user;
		this.song = song;
		this.addedAt = addedAt;
	}
}