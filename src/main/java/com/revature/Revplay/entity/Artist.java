package com.revature.Revplay.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long artistId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String genre;

    @Column(name = "banner_image")
    private String bannerImage;

    @Column(name = "instagram_link")
    private String instagramLink;

    @Column(name = "twitter_link")
    private String twitterLink;

    @Column(name = "spotify_link")
    private String spotifyLink;

    @Column(name = "website_link")
    private String websiteLink;
    
    @Column(name = "follower_count")
    private Long followerCount = 0L;

	public Artist() {
	
	}

	public Artist(Long artistId, User user, String artistName, String bio, String genre, String bannerImage,
			String instagramLink, String twitterLink, String spotifyLink, String websiteLink, Long followerCount) {
		super();
		this.artistId = artistId;
		this.user = user;
		this.artistName = artistName;
		this.bio = bio;
		this.genre = genre;
		this.bannerImage = bannerImage;
		this.instagramLink = instagramLink;
		this.twitterLink = twitterLink;
		this.spotifyLink = spotifyLink;
		this.websiteLink = websiteLink;
		this.followerCount = followerCount;
	}

	public Long getArtistId() {
		return artistId;
	}

	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getBannerImage() {
		return bannerImage;
	}

	public void setBannerImage(String bannerImage) {
		this.bannerImage = bannerImage;
	}

	public String getInstagramLink() {
		return instagramLink;
	}

	public void setInstagramLink(String instagramLink) {
		this.instagramLink = instagramLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String getSpotifyLink() {
		return spotifyLink;
	}

	public void setSpotifyLink(String spotifyLink) {
		this.spotifyLink = spotifyLink;
	}

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}

	public Long getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(Long followerCount) {
		this.followerCount = followerCount;
	}
	
	
	
}

  





