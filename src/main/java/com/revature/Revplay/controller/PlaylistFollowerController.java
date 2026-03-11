
package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.revature.Revplay.entity.PlaylistFollower;
import com.revature.Revplay.service.PlaylistFollowerService;

@RestController
@RequestMapping("/playlist-followers")
public class PlaylistFollowerController {

    private final PlaylistFollowerService service;

    public PlaylistFollowerController(PlaylistFollowerService service) {
        this.service = service;
    }

    @PostMapping
    public PlaylistFollower add(@RequestBody PlaylistFollower follower) {
        return service.save(follower);
    }

    @GetMapping
    public List<PlaylistFollower> getAll() {
        return service.findAll();
    }
}
