package io.github.tysker.notifyhub.search.mapping;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotificationIndexDefinitionTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NotificationIndexDefinition definition = new NotificationIndexDefinition(objectMapper);

    @Test
    void printBody() {
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(definition.createIndexBody()));
    }

    @Test
    void mappingsMatchDevToolsVersion() {
        JsonNode expected = objectMapper.readTree("""
                {
                    "dynamic": "strict",
                    "properties": {
                        "id":         { "type": "keyword" },
                        "channel":    { "type": "keyword" },
                        "recipient":  { "type": "keyword" },
                    "subject": {
                      "type": "text",
                      "fields": { "keyword": { "type": "keyword" } }
                    },
                    "message": {
                      "type": "text",
                      "fields": { "keyword": { "type": "keyword" } }
                    },
                    "status":     { "type": "keyword" },
                    "retries":    { "type": "integer" },
                    "durationMs": { "type": "long" },
                    "createdAt":  { "type": "date" },
                    "template":   { "type": "keyword" }
                }
                }
                """);

        assertEquals(expected, definition.mappings());
    }

    @Test
    void settingsMatchDevToolsVersion() {
        JsonNode expected = objectMapper.readTree("""
                {
                    "number_of_shards": 1,
                    "number_of_replicas": 0
                }
                """);

        assertEquals(expected, definition.settings());
    }
}