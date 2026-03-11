package com.revature.Revplay.dto;

public class ArtistSearchDTO {

    private Long artistId;
    private String artistName;
    private String bannerImage;

    public ArtistSearchDTO(Long artistId, String artistName, String bannerImage) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.bannerImage = bannerImage;
    }

    public Long getArtistId() { return artistId; }
    public String getArtistName() { return artistName; }
    public String getBannerImage() { return bannerImage; }
}