package com.betagames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betagames.model.Editors;

/**
 *
 * @author Fabrini Marco
 */
@Repository
public interface IEditorsRepository extends JpaRepository<Editors, Integer> {
    @Query(name = "editors.searchByTyping")
    List<Editors> searchByTyping(@Param("id") Integer id,
                                 @Param("name") String name,
                                 @Param("website") String website);

    Optional<Editors> findByName(String name);

    Optional<Editors> findByWebsite(String website);
}// interface
