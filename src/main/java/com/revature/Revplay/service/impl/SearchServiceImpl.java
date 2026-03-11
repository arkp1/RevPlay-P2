package com.revature.Revplay.service.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.revature.Revplay.dto.*;
import com.revature.Revplay.repository.*;
import com.revature.Revplay.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final PlaylistRepository playlistRepository;
    private static final Logger logger = LogManager.getLogger(SearchServiceImpl.class);

    public SearchServiceImpl(SongRepository songRepository,
                             ArtistRepository artistRepository,
                             AlbumRepository albumRepository,
                             PlaylistRepository playlistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public GlobalSearchDTO search(String keyword) {
    	logger.info("Global search triggered with keyword={}", keyword);

        List<SongSearchDTO> songs = songRepository.searchSongs(keyword);
        List<ArtistSearchDTO> artists = artistRepository.searchArtists(keyword);
        List<AlbumSearchDTO> albums = albumRepository.searchAlbums(keyword);
        List<PlaylistSearchDTO> playlists = playlistRepository.searchPlaylists(keyword);

        return new GlobalSearchDTO(songs, artists, albums, playlists);
    }
}
