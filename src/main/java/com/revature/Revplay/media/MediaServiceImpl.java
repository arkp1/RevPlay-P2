package com.revature.Revplay.media;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MediaServiceImpl implements MediaService {

    private final MediaStorageProperties properties;

    public MediaServiceImpl(MediaStorageProperties properties) {
        this.properties = properties;
    }

    @Override
    public String upload(MultipartFile file, MediaType type) {
        try {
            if (file == null || file.isEmpty()) {
                return null;
            }

            String originalName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
            String filename = System.currentTimeMillis() + "_" + originalName;

            Path uploadPath = Paths.get(properties.getRoot() + type.getFolder());

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(filename);
            Files.write(filePath, file.getBytes());

            return "/uploads/" + type.getFolder() + "/" + filename;

        } catch (Exception e) {
            throw new RuntimeException("File upload failed");
        }
    }
}