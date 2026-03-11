package com.revature.Revplay.entity;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long songId;

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Column(nullable = false)
    private String title;

    private String genre;

    private Integer duration;

    @Column(name = "audio_file_path", nullable = false)
    private String audioFilePath;

    @Column(name = "play_count")
    private Long playCount;
    
    private Long likeCount = 0L;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public Song() { }

	public Song(Long songId, Artist artist, Album album, String title, String genre, Integer duration,
			String audioFilePath, Long playCount, LocalDate releaseDate, LocalDateTime createdAt, Long likeCount) {
		this.songId = songId;
		this.artist = artist;
		this.album = album;
		this.title = title;
		this.genre = genre;
		this.duration = duration;
		this.audioFilePath = audioFilePath;
		this.playCount = playCount;
		this.releaseDate = releaseDate;
		this.createdAt = createdAt;
		this.likeCount = likeCount;
	}

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getAudioFilePath() {
		return audioFilePath;
	}

	public void setAudioFilePath(String audioFilePath) {
		this.audioFilePath = audioFilePath;
	}

	public Long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}
	
	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    
}
