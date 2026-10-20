package io.github.tysker.notifyhub.search.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@Component
@RequiredArgsConstructor
public class NotificationIndexDefinition {

    private final ObjectMapper objectMapper;
    public static final int VERSION = 2;

    public ObjectNode settings() {
        ObjectNode settings = objectMapper.createObjectNode();
        settings.put("number_of_shards", 1);
        settings.put("number_of_replicas", 0);
        return settings;
    }

    public ObjectNode mappings() {
        return MappingBuilder.strict(objectMapper)
                .keyword("id")
                .keyword("channel")
                .keyword("recipient")
                .textWithKeyword("subject")
                .textWithKeyword("message")
                .keyword("status")
                .integer("retries")
                .longField("durationMs")
                .date("createdAt")
                .keyword("template")
                .build();
    }

    public ObjectNode createIndexBody() {
        ObjectNode body = objectMapper.createObjectNode();
        body.set("settings", settings());
        body.set("mappings", mappings());
        return body;
    }
}
