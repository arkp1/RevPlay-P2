package com.revature.Revplay.dto;

public class PlaylistSearchDTO {

    private Long playlistId;
    private String name;

    public PlaylistSearchDTO(Long playlistId, String name) {
        this.playlistId = playlistId;
        this.name = name;
    }

    public Long getPlaylistId() { return playlistId; }
    public String getName() { return name; }
}