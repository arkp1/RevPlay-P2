
package com.revature.Revplay.dto;

public class PodcastEpisodeDTO {

    private Long id;

    public PodcastEpisodeDTO() {}

    public PodcastEpisodeDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
