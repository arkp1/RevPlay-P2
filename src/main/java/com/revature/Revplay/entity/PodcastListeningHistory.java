
package com.revature.Revplay.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "podcast_listening_history")
public class PodcastListeningHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "episode_id", nullable = false)
    private PodcastEpisode episode;

    @Column(name = "played_at", insertable = false, updatable = false)
    private LocalDateTime playedAt;

    public PodcastListeningHistory() {}

	public PodcastListeningHistory(Long historyId, User user, PodcastEpisode episode, LocalDateTime playedAt) {
		this.historyId = historyId;
		this.user = user;
		this.episode = episode;
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

	public PodcastEpisode getEpisode() {
		return episode;
	}

	public void setEpisode(PodcastEpisode episode) {
		this.episode = episode;
	}

	public LocalDateTime getPlayedAt() {
		return playedAt;
	}

	public void setPlayedAt(LocalDateTime playedAt) {
		this.playedAt = playedAt;
	}
    
    
}
