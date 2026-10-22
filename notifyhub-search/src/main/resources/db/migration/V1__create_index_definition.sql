CREATE TABLE index_definition
(
    id         BIGSERIAL PRIMARY KEY,
    alias      VARCHAR(255) NOT NULL,
    version    INTEGER      NOT NULL,
    settings   JSONB        NOT NULL,
    mappings   JSONB        NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT uq_index_definition_alias_version UNIQUE (alias, version)
);