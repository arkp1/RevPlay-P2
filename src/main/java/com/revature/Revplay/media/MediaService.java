package com.revature.Revplay.media;



import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    String upload(MultipartFile file, MediaType type);

}