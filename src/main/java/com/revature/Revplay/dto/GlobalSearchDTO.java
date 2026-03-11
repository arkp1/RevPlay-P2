package com.revature.Revplay.dto;

import java.util.List;

public class GlobalSearchDTO {

    private List<SongSearchDTO> songs;
    private List<ArtistSearchDTO> artists;
    private List<AlbumSearchDTO> albums;
    private List<PlaylistSearchDTO> playlists;

    public GlobalSearchDTO(List<SongSearchDTO> songs,
                           List<ArtistSearchDTO> artists,
                           List<AlbumSearchDTO> albums,
                           List<PlaylistSearchDTO> playlists) {
        this.songs = songs;
        this.artists = artists;
        this.albums = albums;
        this.playlists = playlists;
    }

    public List<SongSearchDTO> getSongs() { return songs; }
    public List<ArtistSearchDTO> getArtists() { return artists; }
    public List<AlbumSearchDTO> getAlbums() { return albums; }
    public List<PlaylistSearchDTO> getPlaylists() { return playlists; }
}