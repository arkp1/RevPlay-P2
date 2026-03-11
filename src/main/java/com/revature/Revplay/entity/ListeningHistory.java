package com.revature.Revplay.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
	@Table(name="listening_history")
	public class ListeningHistory {

	    @Id
	    @GeneratedValue(strategy=GenerationType.IDENTITY)
	    @Column(name="history_id")
	    private Long historyId;

	    @ManyToOne
	    @JoinColumn(name="user_id",nullable=false)
	    private User user;

	    @ManyToOne
	    @JoinColumn(name="song_id",nullable=false)
	    private Song song;

	    @Column(name="played_at", insertable=false, updatable=false)
	    private LocalDateTime playedAt;

		public ListeningHistory() {
		}

		public ListeningHistory(Long historyId, User user, Song song, LocalDateTime playedAt) {
			this.historyId = historyId;
			this.user = user;
			this.song = song;
			this.playedAt = playedAt;
		}

		public Long getHistoryId() {
			return historyId;
		}

		public void setHistoryId(Long historyId) {
			this.historyId = historyId;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Song getSong() {
			return song;
		}

		public void setSong(Song song) {
			this.song = song;
		}

		public LocalDateTime getPlayedAt() {
			return playedAt;
		}

		public void setPlayedAt(LocalDateTime playedAt) {
			this.playedAt = playedAt;
		}
	    
	    
	}


