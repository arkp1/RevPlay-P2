package com.revature.Revplay.dto;

public class AlbumSearchDTO {

    private Long albumId;
    private String title;

    public AlbumSearchDTO(Long albumId, String title) {
        this.albumId = albumId;
        this.title = title;
    }

    public Long getAlbumId() { return albumId; }
    public String getTitle() { return title; }
}