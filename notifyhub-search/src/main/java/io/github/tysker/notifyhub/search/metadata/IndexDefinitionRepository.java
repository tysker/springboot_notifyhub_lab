package io.github.tysker.notifyhub.search.metadata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndexDefinitionRepository extends JpaRepository<IndexDefinitionEntity, Long> {

    Optional<IndexDefinitionEntity> findByAliasAndVersion(String alias, int version);
}