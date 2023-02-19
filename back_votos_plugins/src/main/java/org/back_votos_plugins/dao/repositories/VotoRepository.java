package org.back_votos_plugins.dao.repositories;

import org.back_votos_plugins.dao.tables.VotoTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VotoRepository extends JpaRepository<VotoTable, UUID> {
}
