package com.revature.Revplay.controller;

import com.revature.Revplay.entity.Album;
import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.entity.Podcast;
import com.revature.Revplay.entity.PodcastEpisode;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.media.MediaService;
import com.revature.Revplay.media.MediaType;
import com.revature.Revplay.repository.AlbumRepository;
import com.revature.Revplay.repository.ArtistRepository;
import com.revature.Revplay.repository.PodcastRepository;
import com.revature.Revplay.repository.SongRepository;
import com.revature.Revplay.security.JwtUtil;
import com.revature.Revplay.service.PodcastEpisodeService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/artist")
public class ArtistUploadController {

    private final JwtUtil jwtUtil;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final PodcastRepository podcastRepository;
    private final PodcastEpisodeService podcastEpisodeService;
    private final MediaService mediaService;

    public ArtistUploadController(JwtUtil jwtUtil,
                                  ArtistRepository artistRepository,
                                  AlbumRepository albumRepository,
                                  SongRepository songRepository,
                                  PodcastRepository podcastRepository,
                                  PodcastEpisodeService podcastEpisodeService,
                                  MediaService mediaService) {
        this.jwtUtil = jwtUtil;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.podcastRepository = podcastRepository;
        this.podcastEpisodeService = podcastEpisodeService;
        this.mediaService = mediaService;
    }

    //  Helper: resolve artist from JWT cookie 
    private Artist resolveArtist(String token) {
        if (token == null || !jwtUtil.validateToken(token)) return null;
        Claims claims = jwtUtil.extractClaims(token);
        String role = claims.get("role", String.class);
        if (!"ARTIST".equals(role)) return null;
        Long userId = claims.get("userId", Long.class);
        return artistRepository.findByUser_UserId(userId).orElse(null);
    }

    // ═══════════════════════════════════════════
    //  UPLOAD SONG
    // ═══════════════════════════════════════════
    @PostMapping("/upload/song")
    public String uploadSong(
            @CookieValue(name = "jwt", required = false) String token,
            @RequestParam("title")       String title,
            @RequestParam("genre")       String genre,
            @RequestParam("releaseDate") String releaseDate,
            @RequestParam(value = "albumId", required = false) Long albumId,
            @RequestParam("audioFile")   MultipartFile audioFile,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        String audioPath = mediaService.upload(audioFile, MediaType.SONG_AUDIO);
        if (audioPath == null) {
            ra.addFlashAttribute("error", "Audio file is required.");
            return "redirect:/artist-dashboard";
        }

        Song song = new Song();
        song.setTitle(title);
        song.setGenre(genre);
        song.setAudioFilePath(audioPath);
        song.setPlayCount(0L);
        song.setLikeCount(0L);
        song.setArtist(artist);
        if (releaseDate != null && !releaseDate.isBlank())
            song.setReleaseDate(LocalDate.parse(releaseDate));
        if (albumId != null)
            albumRepository.findById(albumId).ifPresent(song::setAlbum);

        songRepository.save(song);
        ra.addFlashAttribute("success", "Song \"" + title + "\" uploaded successfully!");
        return "redirect:/artist-dashboard";
    }

    //  CREATE ALBUM
    @PostMapping("/upload/album")
    public String createAlbum(
            @CookieValue(name = "jwt", required = false) String token,
            @RequestParam("title")       String title,
            @RequestParam("description") String description,
            @RequestParam("releaseDate") String releaseDate,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Album album = new Album();
        album.setTitle(title);
        album.setDescription(description);
        album.setArtist(artist);
        if (releaseDate != null && !releaseDate.isBlank())
            album.setReleaseDate(LocalDate.parse(releaseDate));

        if (coverImage != null && !coverImage.isEmpty()) {
            String coverPath = mediaService.upload(coverImage, MediaType.ALBUM_COVER);
            album.setCoverImage(coverPath);
        }

        albumRepository.save(album);
        ra.addFlashAttribute("success", "Album \"" + title + "\" created successfully!");
        return "redirect:/artist-dashboard";
    }

    //  CREATE PODCAST (series)
    @PostMapping("/upload/podcast")
    public String createPodcast(
            @CookieValue(name = "jwt", required = false) String token,
            @RequestParam("title")       String title,
            @RequestParam("description") String description,
            @RequestParam("category")    String category,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Podcast podcast = new Podcast();
        podcast.setTitle(title);
        podcast.setDescription(description);
        podcast.setCategory(category);
        podcast.setArtist(artist);

        if (coverImage != null && !coverImage.isEmpty()) {
            String coverPath = mediaService.upload(coverImage, MediaType.PODCAST_AUDIO);
            podcast.setCoverImage(coverPath);
        }

        podcastRepository.save(podcast);
        ra.addFlashAttribute("success", "Podcast \"" + title + "\" created!");
        return "redirect:/artist-dashboard";
    }

    //  EDIT SONG
    @PostMapping("/edit/song/{id}")
    public String editSong(
            @CookieValue(name = "jwt", required = false) String token,
            @PathVariable Long id,
            @RequestParam("title")       String title,
            @RequestParam("genre")       String genre,
            @RequestParam("releaseDate") String releaseDate,
            @RequestParam(value = "albumId", required = false) Long albumId,
            @RequestParam(value = "audioFile", required = false) MultipartFile audioFile,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Song song = songRepository.findById(id).orElse(null);
        if (song == null || !song.getArtist().getArtistId().equals(artist.getArtistId())) {
            ra.addFlashAttribute("error", "Song not found or not yours.");
            return "redirect:/artist-dashboard";
        }

        song.setTitle(title);
        song.setGenre(genre);
        if (releaseDate != null && !releaseDate.isBlank())
            song.setReleaseDate(LocalDate.parse(releaseDate));
        if (albumId != null)
            albumRepository.findById(albumId).ifPresent(song::setAlbum);
        else
            song.setAlbum(null);

        if (audioFile != null && !audioFile.isEmpty()) {
            String audioPath = mediaService.upload(audioFile, MediaType.SONG_AUDIO);
            if (audioPath != null) song.setAudioFilePath(audioPath);
        }

        songRepository.save(song);
        ra.addFlashAttribute("success", "Song \"" + title + "\" updated!");
        return "redirect:/artist-dashboard#library";
    }

    //  DELETE SONG
    @PostMapping("/delete/song/{id}")
    public String deleteSong(
            @CookieValue(name = "jwt", required = false) String token,
            @PathVariable Long id,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Song song = songRepository.findById(id).orElse(null);
        if (song == null || !song.getArtist().getArtistId().equals(artist.getArtistId())) {
            ra.addFlashAttribute("error", "Song not found or not yours.");
            return "redirect:/artist-dashboard";
        }
        songRepository.delete(song);
        ra.addFlashAttribute("success", "Song deleted.");
        return "redirect:/artist-dashboard#library";
    }

    //  EDIT ALBUM
    @PostMapping("/edit/album/{id}")
    public String editAlbum(
            @CookieValue(name = "jwt", required = false) String token,
            @PathVariable Long id,
            @RequestParam("title")       String title,
            @RequestParam("description") String description,
            @RequestParam("releaseDate") String releaseDate,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Album album = albumRepository.findById(id).orElse(null);
        if (album == null || !album.getArtist().getArtistId().equals(artist.getArtistId())) {
            ra.addFlashAttribute("error", "Album not found or not yours.");
            return "redirect:/artist-dashboard";
        }

        album.setTitle(title);
        album.setDescription(description);
        if (releaseDate != null && !releaseDate.isBlank())
            album.setReleaseDate(LocalDate.parse(releaseDate));

        if (coverImage != null && !coverImage.isEmpty()) {
            String coverPath = mediaService.upload(coverImage, MediaType.ALBUM_COVER);
            if (coverPath != null) album.setCoverImage(coverPath);
        }

        albumRepository.save(album);
        ra.addFlashAttribute("success", "Album \"" + title + "\" updated!");
        return "redirect:/artist-dashboard#library";
    }

    //  DELETE ALBUM

    @PostMapping("/delete/album/{id}")
    public String deleteAlbum(
            @CookieValue(name = "jwt", required = false) String token,
            @PathVariable Long id,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Album album = albumRepository.findById(id).orElse(null);
        if (album == null || !album.getArtist().getArtistId().equals(artist.getArtistId())) {
            ra.addFlashAttribute("error", "Album not found or not yours.");
            return "redirect:/artist-dashboard";
        }
        albumRepository.delete(album);
        ra.addFlashAttribute("success", "Album deleted.");
        return "redirect:/artist-dashboard#library";
    }


    //  EDIT PODCAST

    @PostMapping("/edit/podcast/{id}")
    public String editPodcast(
            @CookieValue(name = "jwt", required = false) String token,
            @PathVariable Long id,
            @RequestParam("title")       String title,
            @RequestParam("description") String description,
            @RequestParam("category")    String category,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Podcast podcast = podcastRepository.findById(id).orElse(null);
        if (podcast == null || !podcast.getArtist().getArtistId().equals(artist.getArtistId())) {
            ra.addFlashAttribute("error", "Podcast not found or not yours.");
            return "redirect:/artist-dashboard";
        }

        podcast.setTitle(title);
        podcast.setDescription(description);
        podcast.setCategory(category);

        if (coverImage != null && !coverImage.isEmpty()) {
            String coverPath = mediaService.upload(coverImage, MediaType.PODCAST_AUDIO);
            if (coverPath != null) podcast.setCoverImage(coverPath);
        }

        podcastRepository.save(podcast);
        ra.addFlashAttribute("success", "Podcast \"" + title + "\" updated!");
        return "redirect:/artist-dashboard#library";
    }


    //  DELETE PODCAST
   
    @PostMapping("/delete/podcast/{id}")
    public String deletePodcast(
            @CookieValue(name = "jwt", required = false) String token,
            @PathVariable Long id,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Podcast podcast = podcastRepository.findById(id).orElse(null);
        if (podcast == null || !podcast.getArtist().getArtistId().equals(artist.getArtistId())) {
            ra.addFlashAttribute("error", "Podcast not found or not yours.");
            return "redirect:/artist-dashboard";
        }
        podcastRepository.delete(podcast);
        ra.addFlashAttribute("success", "Podcast deleted.");
        return "redirect:/artist-dashboard#library";
    }

    //  UPLOAD PODCAST EPISODE

    @PostMapping("/upload/episode")
    public String uploadEpisode(
            @CookieValue(name = "jwt", required = false) String token,
            @RequestParam("podcastId")   Long podcastId,
            @RequestParam("title")       String title,
            @RequestParam("description") String description,
            @RequestParam("duration")    Integer duration,
            @RequestParam("releaseDate") String releaseDate,
            @RequestParam("audioFile")   MultipartFile audioFile,
            RedirectAttributes ra) {

        Artist artist = resolveArtist(token);
        if (artist == null) return "redirect:/login";

        Podcast podcast = podcastRepository.findById(podcastId).orElse(null);
        if (podcast == null || !podcast.getArtist().getArtistId().equals(artist.getArtistId())) {
            ra.addFlashAttribute("error", "Podcast not found or not yours.");
            return "redirect:/artist-dashboard";
        }

        String audioPath = mediaService.upload(audioFile, MediaType.PODCAST_AUDIO);
        if (audioPath == null) {
            ra.addFlashAttribute("error", "Audio file is required.");
            return "redirect:/artist-dashboard";
        }

        PodcastEpisode episode = new PodcastEpisode();
        episode.setPodcast(podcast);
        episode.setTitle(title);
        episode.setDescription(description);
        episode.setDuration(duration);
        episode.setPlayCount(0L);
        episode.setAudioFilePath(audioPath);
        if (releaseDate != null && !releaseDate.isBlank())
            episode.setReleaseDate(LocalDate.parse(releaseDate));

        podcastEpisodeService.save(episode);
        ra.addFlashAttribute("success", "Episode \"" + title + "\" uploaded!");
        return "redirect:/artist-dashboard";
    }
}