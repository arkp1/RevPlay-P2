package com.revature.Revplay.entity;

import com.revature.Revplay.entity.compositekey.PlaylistSongId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="playlist_songs")
public class PlaylistSong {

    @EmbeddedId
    private PlaylistSongId id;

    @ManyToOne
    @MapsId("playlistId")
    @JoinColumn(name="playlist_id")
    private Playlist playlist;

    @ManyToOne
    @MapsId("songId")
    @JoinColumn(name="song_id")
    private Song song;

    private Integer position;

	public PlaylistSong() {
	}

	public PlaylistSong(PlaylistSongId id, Playlist playlist, Song song, Integer position) {
		this.id = id;
		this.playlist = playlist;
		this.song = song;
		this.position = position;
	}

	public PlaylistSongId getId() {
		return id;
	}

	public void setId(PlaylistSongId id) {
		this.id = id;
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
    
    
}