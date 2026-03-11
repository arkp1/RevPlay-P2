package com.revature.Revplay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.revature.Revplay.entity.Podcast;
import com.revature.Revplay.service.PodcastService;

@Controller
public class PodcastViewController {

    private final PodcastService podcastService;

    public PodcastViewController(PodcastService podcastService) {
        this.podcastService = podcastService;
    }

    @GetMapping("/view/podcast/{id}")
    public String podcastDetail(@PathVariable Long id, Model model) {
        Podcast podcast = podcastService.findById(id);
        model.addAttribute("podcast", podcast);
        return "podcast-detail";
    }
}