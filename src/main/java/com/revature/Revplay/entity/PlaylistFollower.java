
package com.revature.Revplay.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.revature.Revplay.entity.compositekey.PlaylistFollowerId;

@Entity
@Table(name = "playlist_followers")
public class PlaylistFollower {

    @EmbeddedId
    private PlaylistFollowerId id;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "followed_at", insertable = false, updatable = false)
    private LocalDateTime followedAt;

    public PlaylistFollower() {}

	public PlaylistFollower(PlaylistFollowerId id, Playlist playlist, User user, LocalDateTime followedAt) {
		this.id = id;
		this.playlist = playlist;
		this.user = user;
		this.followedAt = followedAt;
	}

	public PlaylistFollowerId getId() {
		return id;
	}

	public void setId(PlaylistFollowerId id) {
		this.id = id;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getFollowedAt() {
		return followedAt;
	}

	public void setFollowedAt(LocalDateTime followedAt) {
		this.followedAt = followedAt;
	}
    
    
}
