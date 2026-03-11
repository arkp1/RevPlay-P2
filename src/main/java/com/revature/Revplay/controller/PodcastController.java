package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.revature.Revplay.entity.Podcast;
import com.revature.Revplay.service.PodcastService;

@RestController
@RequestMapping("/podcast")
public class PodcastController {

    private final PodcastService service;

    public PodcastController(PodcastService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping("/add")
    public Podcast add(@RequestBody Podcast obj) {
        return service.save(obj);
    }

    // READ ALL
    @GetMapping("/all")
    public List<Podcast> getAll() {
        return service.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Podcast getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public Podcast update(@PathVariable Long id, @RequestBody Podcast obj) {
        obj.setPodcastId(id);  // enforce correct ID
        return service.save(obj);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Podcast deleted successfully";
    }
}