package com.revature.Revplay.media;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/media")
public class MediaUploadController {

    private final MediaService mediaService;

    public MediaUploadController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload/{type}")
    public String uploadFile(
            @PathVariable MediaType type,
            @RequestParam("file") MultipartFile file
    ) {

        return mediaService.upload(file, type);
    }
}