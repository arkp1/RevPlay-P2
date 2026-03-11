package com.revature.Revplay.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.revature.Revplay.entity.Album;
import com.revature.Revplay.service.AlbumService;

@RestController
@RequestMapping("/album")
public class AlbumController {

    private final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    // CREATE
    @PostMapping("/add")
    public Album add(@RequestBody Album obj) {
        return service.save(obj);
    }

    // READ ALL
    @GetMapping("/all")
    public List<Album> getAll() {
        return service.findAll();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Album getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // UPDATE 
    @PutMapping("/update/{id}")
    public Album update(@PathVariable Long id, @RequestBody Album obj) {
        obj.setAlbumId(id);   // force correct id
        return service.save(obj);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Album deleted successfully";
    }
}