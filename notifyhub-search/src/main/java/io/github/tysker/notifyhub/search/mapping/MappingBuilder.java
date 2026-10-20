package io.github.tysker.notifyhub.search.mapping;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

public class MappingBuilder {

    private final ObjectNode root;
    private final ObjectNode properties;

    private MappingBuilder(ObjectMapper objectMapper, String dynamic) {
        this.root = objectMapper.createObjectNode();
        this.root.put("dynamic", dynamic);
        this.properties = this.root.putObject("properties");
    }

    public static MappingBuilder strict(ObjectMapper objectMapper) {
        return new MappingBuilder(objectMapper, "strict");
    }

    public MappingBuilder keyword(String name) {
        return field(name, "keyword");
    }

    public MappingBuilder integer(String name) {
        return field(name, "integer");
    }

    public MappingBuilder longField(String name) {
        return field(name, "long");
    }

    public MappingBuilder date(String name) {
        return field(name, "date");
    }

    public MappingBuilder textWithKeyword(String name) {
        ObjectNode field = properties.putObject(name);
        field.put("type", "text");
        field.putObject("fields")
                .putObject("keyword")
                .put("type", "keyword");
        return this;
    }

    private MappingBuilder field(String name, String type) {
        properties.putObject(name).put("type", type);
        return this;
    }

    public ObjectNode build() {
        return root;
    }
}
