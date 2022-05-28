package com.github.vendigo.musicfriends.controller;

import com.github.vendigo.musicfriends.model.ArtistNode;
import com.github.vendigo.musicfriends.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import org.neo4j.driver.internal.value.PathValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ArtistController {

    private final ArtistRepository artistRepository;

    @GetMapping("/artist/find")
    public List<ArtistNode> findArtistByName(@RequestParam("name") String name) {
        return artistRepository.findByNameLike(name);
    }

    @GetMapping("/path")
    public List<ArtistNode> findPath(@RequestParam("from") String from, @RequestParam("to") String to) {
        PathValue path = (PathValue) artistRepository.findShortestPath(from, to)
                .orElse(null);
        return List.of();
    }
}
