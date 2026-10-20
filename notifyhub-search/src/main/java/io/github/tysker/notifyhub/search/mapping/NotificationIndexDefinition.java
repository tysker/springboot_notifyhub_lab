package io.github.tysker.notifyhub.search.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@Component
@RequiredArgsConstructor
public class NotificationIndexDefinition {

    private final ObjectMapper objectMapper;

    public ObjectNode settings() {
        ObjectNode settings = objectMapper.createObjectNode();
        settings.put("number_of_shards", 1);
        settings.put("number_of_replicas", 0);
        return settings;
    }

    public ObjectNode mappings() {
        ObjectNode mappings = objectMapper.createObjectNode();
        mappings.put("dynamic", "strict");

        ObjectNode properties = mappings.putObject("properties");

        properties.putObject("id").put("type", "keyword");
        properties.putObject("channel").put("type", "keyword");
        properties.putObject("recipient").put("type", "keyword");

        ObjectNode subject = properties.putObject("subject");
        subject.put("type", "text");
        subject.putObject("fields")
                .putObject("keyword")
                .put("type", "keyword");

        ObjectNode message = properties.putObject("message");
        message.put("type", "text");
        message.putObject("fields")
                .putObject("keyword")
                .put("type", "keyword");

        properties.putObject("status").put("type", "keyword");
        properties.putObject("retries").put("type", "integer");
        properties.putObject("durationMs").put("type", "long");
        properties.putObject("createdAt").put("type", "date");
        properties.putObject("template").put("type", "keyword");

        return mappings;
    }

    public ObjectNode createIndexBody() {
        ObjectNode body = objectMapper.createObjectNode();
        body.set("settings", settings());
        body.set("mappings", mappings());
        return body;
    }
}
