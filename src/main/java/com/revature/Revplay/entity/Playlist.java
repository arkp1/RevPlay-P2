package com.revature.Revplay.entity;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long playlistId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private PlaylistPrivacy privacy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

	public Playlist() {
	
	}

	public Playlist(Long playlistId, User user, String name, String description, PlaylistPrivacy privacy,
			LocalDateTime createdAt) {
		super();
		this.playlistId = playlistId;
		this.user = user;
		this.name = name;
		this.description = description;
		this.privacy = privacy;
		this.createdAt = createdAt;
	}

	public Long getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(Long playlistId) {
		this.playlistId = playlistId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PlaylistPrivacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(PlaylistPrivacy privacy) {
		this.privacy = privacy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
	
	

    