
package com.revature.Revplay.dto;

public class PlaylistSongDTO {

    private Long playlistId;
    private Long songId;
    private Integer position;

    public PlaylistSongDTO() {}

    public Long getPlaylistId() { return playlistId; }
    public void setPlaylistId(Long playlistId) { this.playlistId = playlistId; }

    public Long getSongId() { return songId; }
    public void setSongId(Long songId) { this.songId = songId; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
}
