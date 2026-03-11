package com.revature.Revplay.dto;

public class ReorderPlaylistSongDTO {

    private Long songId;
    private Integer newPosition;

    public Long getSongId() { return songId; }
    public void setSongId(Long songId) { this.songId = songId; }

    public Integer getNewPosition() { return newPosition; }
    public void setNewPosition(Integer newPosition) { this.newPosition = newPosition; }
}
