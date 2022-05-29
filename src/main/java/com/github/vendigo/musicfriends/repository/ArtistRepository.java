package com.github.vendigo.musicfriends.repository;

import com.github.vendigo.musicfriends.model.ArtistNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends Neo4jRepository<ArtistNode, Long> {

    @Query("MATCH (a:Artist) WHERE toLower(a.name) starts with $namePart RETURN a ORDER BY a.fans DESC LIMIT 20")
    List<ArtistNode> findByName(String namePart);

    @Query("MATCH p = shortestPath((:Artist{name: $from})<-[*1..20]->(:Artist{name: $to})) RETURN p")
    Optional<Object> findShortestPath(@Param("from") String from, @Param("to") String to);

}
