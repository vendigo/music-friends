package com.github.vendigo.musicfriends.repository;

import com.github.vendigo.musicfriends.model.TrackNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TrackRepository extends Neo4jRepository<TrackNode, Long> {
}
