package com.revature.Revplay.controller;

import java.util.List;


import org.springframework.web.bind.annotation.*;

import com.revature.Revplay.dto.SongSearchDTO;
import com.revature.Revplay.entity.Song;
import com.revature.Revplay.service.SongService;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService service;

    public SongController(SongService service) {
        this.service = service;
    }

    //  CREATE 
    @PostMapping("/add")
    public Song add(@RequestBody Song obj) {
        return service.save(obj);
    }
    // READ ALL 
    @GetMapping("/all")
    public List<Song> getAll() {
        return service.findAll();
    }

    //  READ BY ID 
    @GetMapping("/{id}")
    public Song getById(@PathVariable Long id) {
        return service.findById(id);
    }

    //  UPDATE 
    @PutMapping("/update/{id}")
    public Song update(@PathVariable Long id, @RequestBody Song obj) {
        Song existing = service.findById(id);

        if (existing == null) {
            return null;
        }

        existing.setTitle(obj.getTitle());
        existing.setGenre(obj.getGenre());
        existing.setDuration(obj.getDuration());
        existing.setAudioFilePath(obj.getAudioFilePath());
        existing.setReleaseDate(obj.getReleaseDate());
        existing.setPlayCount(obj.getPlayCount());
        existing.setArtist(obj.getArtist());
        existing.setAlbum(obj.getAlbum());

        return service.save(existing);
    }

    //  DELETE 
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Deleted successfully";
    }
    
    @GetMapping("/top")
    public List<Song> getTopSongs() {
        return service.getTopSongs();
    }
    
    @GetMapping("/search")
    public List<SongSearchDTO> search(@RequestParam String keyword) {
        return service.search(keyword);
    }
}
