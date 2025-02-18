package com.betagames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betagames.model.Games;

/**
 * @author DorigoLorenzo
 **/

 @Repository
public interface IGamesRepository extends JpaRepository<Games, Integer> {

    Optional<Games> findByName(String name);
    
    @Query(name = "games.searchByTyping")
    List<Games> searchByTyping( @Param("gameName") String name,
                                @Param("authorId") Integer authorsId,
                                @Param("categoryId") Integer categoriesId,
                                @Param("editorId") Integer editorId);

    /*
      Query con ricerca per nome: 
        SELECT g FROM Games g \
        LEFT JOIN g.listAuthors a \
        LEFT JOIN g.listCategory c \
        LEFT JOIN g.editor e \
        WHERE (:gameName IS NULL OR LOWER(g.name) LIKE %:gameName%) \
        AND (:authorName IS NULL OR EXISTS (SELECT 1 FROM Authors au JOIN au.listGames g2 WHERE g2.id = g.id AND LOWER(au.name) LIKE %:authorName%)) \
        AND (:categoryName IS NULL OR EXISTS (SELECT 1 FROM Categories ca JOIN ca.listGames g3 WHERE g3.id = g.id AND LOWER(ca.name) LIKE %:categoryName%)) \
        AND (:editorName IS NULL OR (e IS NOT NULL AND LOWER(e.name) LIKE %:editorName%))
    */

}//interface
