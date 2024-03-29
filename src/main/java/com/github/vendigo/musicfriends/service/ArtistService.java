package com.github.vendigo.musicfriends.service;

import com.github.vendigo.musicfriends.model.ArtistNode;
import com.github.vendigo.musicfriends.model.NodeType;
import com.github.vendigo.musicfriends.model.PathNode;
import com.github.vendigo.musicfriends.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Path;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Slf4j
public class ArtistService {

    private static final String LINK_TEMPLATE = "https://www.deezer.com/%s/%d";
    private final ArtistRepository artistRepository;

    @Cacheable("artists")
    public List<ArtistNode> findArtist(String name) {
        if (name.isBlank()) {
            log.info("Searching for most popular artists");
            return artistRepository.findMostPopular();
        }

        log.info("Searching for artist: {}", name);
        return artistRepository.findByName(name.toLowerCase());
    }

    public List<PathNode> findPath(Long from, Long to) {
        Optional<Object> pathResponse = artistRepository.findShortestPath(from, to);
        if (pathResponse.isEmpty()) {
            return List.of();
        }

        Path path = ((PathValue) pathResponse.get()).asPath();
        return StreamSupport.stream(path.nodes().spliterator(), false)
                .map(ArtistService::mapNode)
                .toList();
    }

    private static PathNode mapNode(Node node) {
        if (node.hasLabel(NodeType.ARTIST.getLabel())) {
            return new PathNode(NodeType.ARTIST, node.get("name").asString(),
                    String.format(LINK_TEMPLATE, "artist", node.get("id").asInt()),
                    node.get("picture").asString());
        }

        return new PathNode(NodeType.TRACK, node.get("title").asString(),
                String.format(LINK_TEMPLATE, "track", node.get("id").asInt()),
                null);
    }

    public boolean noCollabs(Long artistId) {
        return artistRepository.countRelations(artistId) == 0;
    }
}
