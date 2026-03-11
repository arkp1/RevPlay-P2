package com.revature.Revplay.dto;

public class SongSearchDTO {

    private Long songId;
    private String title;
    private String artistName;
    private String albumTitle;
    private String genre;
    private Integer duration;
    private String coverImage;

    public SongSearchDTO(Long songId,
                         String title,
                         String artistName,
                         String albumTitle,
                         String genre,
                         Integer duration,
                         String coverImage) {
        this.songId = songId;
        this.title = title;
        this.artistName = artistName;
        this.albumTitle = albumTitle;
        this.genre = genre;
        this.duration = duration;
        this.coverImage = coverImage;
    }

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
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

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}

    
}