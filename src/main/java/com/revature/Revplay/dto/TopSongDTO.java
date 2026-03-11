package com.revature.Revplay.dto;

public class TopSongDTO {

    private Long songId;
    private String title;
    private Long playCount;

    public TopSongDTO(Long songId, String title, Long playCount) {
        this.songId = songId;
        this.title = title;
        this.playCount = playCount;
    }

    public Long getSongId() { return songId; }
    public String getTitle() { return title; }
    public Long getPlayCount() { return playCount; }
}
