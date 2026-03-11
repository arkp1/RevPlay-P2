package com.revature.Revplay.controller;

import org.springframework.web.bind.annotation.*;
import com.revature.Revplay.dto.ArtistAnalyticsDTO;
import com.revature.Revplay.service.ArtistAnalyticsService;

@RestController
@RequestMapping("/analytics")
public class ArtistAnalyticsController {

    private final ArtistAnalyticsService service;

    public ArtistAnalyticsController(ArtistAnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/artist/{artistId}")
    public ArtistAnalyticsDTO getAnalytics(@PathVariable Long artistId) {
        return service.getAnalytics(artistId);
    }
}
