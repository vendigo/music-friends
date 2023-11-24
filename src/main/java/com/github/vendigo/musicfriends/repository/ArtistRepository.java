package com.github.vendigo.musicfriends.repository;

import com.github.vendigo.musicfriends.model.ArtistNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends Neo4jRepository<ArtistNode, Long> {

    @Query("""
            MATCH (a:Artist) WHERE a.lowerName contains $namePart RETURN a
             ORDER BY apoc.text.levenshteinDistance(a.lowerName, $namePart), a.fans DESC LIMIT 20
            """)
    List<ArtistNode> findByName(@Param("namePart") String namePart);

    @Query("MATCH (a:Artist) RETURN a ORDER BY a.fans DESC LIMIT 20")
    List<ArtistNode> findMostPopular();

    @Query("MATCH p = shortestPath((:Artist{id: $from})<-[*1..20]->(:Artist{id: $to})) RETURN p")
    Optional<Object> findShortestPath(@Param("from") Long from, @Param("to") Long to);

    @Query("MATCH p=(:Artist{id: $id})-[r:contribute]->() RETURN count(p)")
    long countRelations(@Param("id") Long id);

}
