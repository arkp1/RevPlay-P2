package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.revature.Revplay.entity.Artist;
import com.revature.Revplay.service.ArtistService;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService service;

    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Artist add(@RequestBody Artist obj) {
        return service.save(obj);
    }

    @GetMapping("/all")
    public List<Artist> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Artist getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/update/{id}")
    public Artist update(@PathVariable Long id,
                         @RequestBody Artist obj) {

        Artist existing = service.findById(id);
        if (existing == null) return null;

        existing.setArtistName(obj.getArtistName());
        existing.setBio(obj.getBio());
        existing.setGenre(obj.getGenre());
        existing.setBannerImage(obj.getBannerImage());
        existing.setInstagramLink(obj.getInstagramLink());
        existing.setTwitterLink(obj.getTwitterLink());
        existing.setSpotifyLink(obj.getSpotifyLink());
        existing.setWebsiteLink(obj.getWebsiteLink());
        existing.setUser(obj.getUser());

        return service.save(existing);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Deleted successfully";
    }
}