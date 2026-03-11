package com.revature.Revplay.dto;

import java.util.List;

public class ArtistAnalyticsDTO {

    private Long artistId;
    private String artistName;
    private Long followerCount;
    private Long totalSongs;
    private Long totalPlays;
    private Long totalLikes;
    private Long playsLast7Days;
    private Long playsLast30Days;
    private List<TopSongDTO> topSongs;

    public ArtistAnalyticsDTO(Long artistId, String artistName, Long followerCount,
                               Long totalSongs, Long totalPlays, Long totalLikes,
                               Long playsLast7Days, Long playsLast30Days,
                               List<TopSongDTO> topSongs) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.followerCount = followerCount;
        this.totalSongs = totalSongs;
        this.totalPlays = totalPlays;
        this.totalLikes = totalLikes;
        this.playsLast7Days = playsLast7Days;
        this.playsLast30Days = playsLast30Days;
        this.topSongs = topSongs;
    }

    public Long getArtistId() { return artistId; }
    public String getArtistName() { return artistName; }
    public Long getFollowerCount() { return followerCount; }
    public Long getTotalSongs() { return totalSongs; }
    public Long getTotalPlays() { return totalPlays; }
    public Long getTotalLikes() { return totalLikes; }
    public Long getPlaysLast7Days() { return playsLast7Days; }
    public Long getPlaysLast30Days() { return playsLast30Days; }
    public List<TopSongDTO> getTopSongs() { return topSongs; }
}
