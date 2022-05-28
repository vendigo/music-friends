package com.github.vendigo.musicfriends.repository;

import com.github.vendigo.musicfriends.model.TrackNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TrackGraphRepository extends Neo4jRepository<TrackNode, Long> {
}
