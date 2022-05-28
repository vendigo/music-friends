package com.github.vendigo.musicfriends.repository;

import com.github.vendigo.musicfriends.model.ArtistNode;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Path;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends Neo4jRepository<ArtistNode, Long> {

    List<ArtistNode> findByNameLike(String name);

    @Query("MATCH p = shortestPath((:Artist{name: $from})<-[*1..20]->(:Artist{name: $to})) return p")
    Optional<Object> findShortestPath(@Param("from") String from, @Param("to") String to);
}
