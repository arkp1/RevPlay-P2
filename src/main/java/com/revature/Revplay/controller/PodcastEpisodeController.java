package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.revature.Revplay.entity.PodcastEpisode;
import com.revature.Revplay.service.PodcastEpisodeService;

@RestController
@RequestMapping("/podcastepisode")
public class PodcastEpisodeController {

    private final PodcastEpisodeService service;

    public PodcastEpisodeController(PodcastEpisodeService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public PodcastEpisode add(@RequestBody PodcastEpisode obj) {
        return service.save(obj);
    }

    @GetMapping("/all")
    public List<PodcastEpisode> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PodcastEpisode getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/update/{id}")
    public PodcastEpisode update(@PathVariable Long id,
                                 @RequestBody PodcastEpisode obj) {

        PodcastEpisode existing = service.findById(id);
        if (existing == null) return null;

        existing.setTitle(obj.getTitle());
        existing.setDescription(obj.getDescription());
        existing.setDuration(obj.getDuration());
        existing.setAudioFilePath(obj.getAudioFilePath());
        existing.setReleaseDate(obj.getReleaseDate());
        existing.setPlayCount(obj.getPlayCount());
        existing.setPodcast(obj.getPodcast());

        return service.save(existing);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Deleted successfully";
    }
}