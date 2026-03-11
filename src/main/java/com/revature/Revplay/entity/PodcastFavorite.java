
package com.revature.Revplay.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.revature.Revplay.entity.compositekey.PodcastFavoriteId;

@Entity
@Table(name = "podcast_favorites")
public class PodcastFavorite {

    @EmbeddedId
    private PodcastFavoriteId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("episodeId")
    @JoinColumn(name = "episode_id")
    private PodcastEpisode episode;

    @Column(name = "favorited_at", insertable = false, updatable = false)
    private LocalDateTime favoritedAt;

    public PodcastFavorite() {}

	public PodcastFavorite(PodcastFavoriteId id, User user, PodcastEpisode episode, LocalDateTime favoritedAt) {
		this.id = id;
		this.user = user;
		this.episode = episode;
		this.favoritedAt = favoritedAt;
	}

	public PodcastFavoriteId getId() {
		return id;
	}

	public void setId(PodcastFavoriteId id) {
		this.id = id;
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

	public LocalDateTime getFavoritedAt() {
		return favoritedAt;
	}

	public void setFavoritedAt(LocalDateTime favoritedAt) {
		this.favoritedAt = favoritedAt;
	}
    
}
