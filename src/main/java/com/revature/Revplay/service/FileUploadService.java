package com.revature.Revplay.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.Revplay.media.MediaService;
import com.revature.Revplay.media.MediaType;

@Service
public class FileUploadService {

    private final MediaService mediaService;

    public FileUploadService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    public String saveProfileImage(MultipartFile file) {

        return mediaService.upload(file, MediaType.PROFILE_IMAGE);

    }
    
    public String saveBannerImage(MultipartFile file) {
        return mediaService.upload(file, MediaType.ARTIST_BANNER);
    }
}