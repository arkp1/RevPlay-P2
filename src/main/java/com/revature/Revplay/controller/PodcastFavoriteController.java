
package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.revature.Revplay.entity.PodcastFavorite;
import com.revature.Revplay.service.PodcastFavoriteService;

@RestController
@RequestMapping("/podcast-favorites")
public class PodcastFavoriteController {

    private final PodcastFavoriteService service;

    public PodcastFavoriteController(PodcastFavoriteService service) {
        this.service = service;
    }

    @PostMapping
    public PodcastFavorite add(@RequestBody PodcastFavorite favorite) {
        return service.save(favorite);
    }

    @GetMapping
    public List<PodcastFavorite> getAll() {
        return service.findAll();
    }
}
