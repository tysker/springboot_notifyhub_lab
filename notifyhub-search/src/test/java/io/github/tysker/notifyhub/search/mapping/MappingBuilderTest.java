package io.github.tysker.notifyhub.search.mapping;

import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MappingBuilderTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void textWithKeywordCreatesSubField() {
        ObjectNode actual = MappingBuilder.strict(objectMapper).textWithKeyword("title").build();

        JsonNode expected = objectMapper.readTree("""
                {
                  "dynamic": "strict",
                  "properties": {
                        "title": {
                          "type": "text",
                          "fields": { "keyword": { "type": "keyword" } }
                        }
                }
                }
                """);

        assertEquals(expected, actual);
    }
}