package com.revature.Revplay.entity;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "podcasts")
public class Podcast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "podcast_id")
    private Long podcastId;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String category;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Podcast() { }

	public Podcast(Long podcastId, Artist artist, String title, String description, String category, String coverImage,
			LocalDateTime createdAt) {
		this.podcastId = podcastId;
		this.artist = artist;
		this.title = title;
		this.description = description;
		this.category = category;
		this.coverImage = coverImage;
		this.createdAt = createdAt;
	}

	public Long getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(Long podcastId) {
		this.podcastId = podcastId;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
