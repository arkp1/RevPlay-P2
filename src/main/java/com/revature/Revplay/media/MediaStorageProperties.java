package com.revature.Revplay.media;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MediaStorageProperties {

    private final String root = "uploads/";

    public String getRoot() {
        return root;
    }
}