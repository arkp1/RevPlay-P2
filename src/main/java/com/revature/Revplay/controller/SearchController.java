package com.revature.Revplay.controller;

import org.springframework.web.bind.annotation.*;
import com.revature.Revplay.dto.GlobalSearchDTO;
import com.revature.Revplay.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public GlobalSearchDTO search(@RequestParam String keyword) {
        return searchService.search(keyword);
    }
}
