package com.github.vendigo.musicfriends.controller;

import com.github.vendigo.musicfriends.model.ArtistNode;
import com.github.vendigo.musicfriends.model.PathNode;
import com.github.vendigo.musicfriends.service.ArtistService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/artist/find")
    public List<ArtistNode> findArtistByName(@RequestParam("name") String name) {
        return artistService.findArtist(name);
    }

    @GetMapping("/path")
    public List<PathNode> findPath(@RequestParam("from") Long from, @RequestParam("to") Long to) {
        return artistService.findPath(from, to);
    }
}
