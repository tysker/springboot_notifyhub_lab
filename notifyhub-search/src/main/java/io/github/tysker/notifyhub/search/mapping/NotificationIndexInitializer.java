package io.github.tysker.notifyhub.search.mapping;

import io.github.tysker.notifyhub.search.config.OpenSearchProperties;
import io.github.tysker.notifyhub.search.service.IndexAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.util.Set;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class NotificationIndexInitializer implements ApplicationRunner {

    private final IndexAdminService indexAdminService;
    private final NotificationIndexDefinition definition;
    private final OpenSearchProperties openSearchProperties;

    @Override
    public void run(ApplicationArguments args) {
        try {
            String alias = openSearchProperties.alias();
            String index = alias + "-v" + NotificationIndexDefinition.VERSION;

            if (indexAdminService.indexExists(index)) {
                log.info("Index {} already exists", index);
                log.info("Live mapping: {}", indexAdminService.getMapping(index));
            } else {
                indexAdminService.createIndex(index, definition.createIndexBody());
            }

            Set<String> targets = indexAdminService.aliasTargets(alias);
            if (targets.isEmpty()) {
                indexAdminService.addAlias(index, alias);
                log.info("Alias {} now points to {}", alias, index);
            } else if (targets.contains(index)) {
                log.info("Alias {} already points to {}", alias, index);
            } else {
                log.warn("Alias {} points to {}, but this version expects {} – switch it manually", alias, targets, index);
            }
        } catch (ResourceAccessException e) {
            String errorMessage = "OpenSearch is not reachable at " + openSearchProperties.baseUrl() + " – is the container running? (docker compose ps)";
            if (openSearchProperties.failOnStartup()) {
                throw new IllegalStateException(errorMessage, e);
            }
            log.warn(errorMessage);
            return;
        }
    }
}
