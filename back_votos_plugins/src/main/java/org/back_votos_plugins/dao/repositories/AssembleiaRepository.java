package org.back_votos_plugins.dao.repositories;

import org.back_votos_plugins.dao.tables.AssembleiaTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssembleiaRepository extends JpaRepository<AssembleiaTable, UUID> {

    @Query("SELECT a FROM AssembleiaTable a JOIN FETCH a.pauta WHERE a.id = :id")
    Optional<AssembleiaTable> findByIdWithPauta(@Param("id") UUID id);
}
