package org.back_votos_plugins.dao.repositories;

import org.back_votos_plugins.dao.tables.VotoTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VotoRepository extends JpaRepository<VotoTable, UUID> {
}
