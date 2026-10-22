package io.github.tysker.notifyhub.search.metadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexDefinitionService {

    private final IndexDefinitionRepository repository;
    private final ObjectMapper objectMapper;

    @Transactional
    public IndexDefinitionEntity ensureStored(String alias,
                                              int version,
                                              ObjectNode settings,
                                              ObjectNode mappings) {
        return repository.findByAliasAndVersion(alias, version)
                .map(existing -> {
                    log.info("Index definition {} v{} already stored (id={})", alias, version, existing.getId());
                    return existing;
                })
                .orElseGet(() -> {
                    IndexDefinitionEntity entity = new IndexDefinitionEntity();
                    entity.setAlias(alias);
                    entity.setVersion(version);
                    entity.setSettings(objectMapper.writeValueAsString(settings));
                    entity.setMappings(objectMapper.writeValueAsString(mappings));
                    IndexDefinitionEntity saved = repository.save(entity);
                    log.info("Stored index definition {} v{} (id={})", alias, version, saved.getId());
                    return saved;
                });
    }

    public ObjectNode mappingsOf(IndexDefinitionEntity entity) {
        objectMapper.readTree(entity.getMappings());
        return objectMapper.createObjectNode();
    }

    public ObjectNode createIndexBody(IndexDefinitionEntity entity) {
        objectMapper.readTree(entity.getSettings());
        objectMapper.readTree(entity.getMappings());
        return objectMapper.createObjectNode();
    }
}
