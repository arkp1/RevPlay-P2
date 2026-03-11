
package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.revature.Revplay.entity.PodcastListeningHistory;
import com.revature.Revplay.service.PodcastListeningHistoryService;

@RestController
@RequestMapping("/podcast-history")
public class PodcastListeningHistoryController {

    private final PodcastListeningHistoryService service;

    public PodcastListeningHistoryController(PodcastListeningHistoryService service) {
        this.service = service;
    }

    @PostMapping
    public PodcastListeningHistory add(@RequestBody PodcastListeningHistory history) {
        return service.save(history);
    }

    @GetMapping
    public List<PodcastListeningHistory> getAll() {
        return service.findAll();
    }
}
