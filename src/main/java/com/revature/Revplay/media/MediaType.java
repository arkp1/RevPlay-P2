package com.revature.Revplay.media;

public enum MediaType {

    PROFILE_IMAGE("profile"),
    ARTIST_BANNER("artist"),
    ALBUM_COVER("album"),
    SONG_AUDIO("songs"),
    PODCAST_AUDIO("podcast");

    private final String folder;

    MediaType(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}