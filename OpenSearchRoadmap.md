# notifyhub – OpenSearch Learning Roadmap

Focus: OpenSearch. Spring Boot is the vehicle, not the goal.
Each checkbox can become its own branch.

Stack (mirrors work): OpenSearch, OpenSearch Dashboards + Dev Tools, Spring Boot, Lombok, UUID,
REST client + JSON, Java classes that build mappings, Postgres for index metadata.

---

## Phase 0 – Setup

- [x] OpenSearch + OpenSearch Dashboards running locally (Docker Compose)
- [x] Postgres in the same Docker Compose
- [x] New module `notifyhub-search`

## Phase 1 – OpenSearch basics in Dev Tools (no Java yet)

- [ ] Index vs indices, documents, `_id`
- [ ] Create / get / delete an index
- [x] Mappings: `text` vs `keyword`, `date`, numbers, multi-fields (`message.keyword`)
- [x] Index settings: shards, replicas, analyzers
- [ ] Index / get / update / delete documents
- [ ] `_bulk`
- [ ] Why a mapping can't be changed → reindexing

## Phase 2 – Talking to OpenSearch from Spring Boot

- [ ] REST client configured via `@Configuration` / `@Bean` + `application.yml`
- [ ] Sending and receiving JSON (Jackson + Lombok classes)
- [ ] Error handling: 404, 400 mapping errors, connection failures
- [ ] UUID as document `_id`

## Phase 3 – Mappings as Java classes

- [ ] Java classes that build the mapping JSON with Jackson `ObjectMapper` + `ObjectNode`
- [ ] Index-creation service that applies mappings on startup
- [ ] Check if index exists / if mapping matches

## Phase 4 – Postgres as source of truth for setup

- [ ] Table(s) for index metadata: name, mapping JSON, version, created date
- [ ] Spring Data JPA (or JDBC) with Postgres
- [ ] "Rebuild" feature: OpenSearch wiped → re-apply everything from Postgres
- [ ] Mapping versioning (v1 → v2)

## Phase 5 – Indexing data

- [ ] Index notifications when notifyhub sends them
- [ ] Data generator (thousands of test documents)
- [ ] Bulk indexing from Java + handling partial failures

## Phase 6 – Search

- [ ] `match`, `match_phrase`, `multi_match`
- [ ] `term`, `terms`, `range`, `exists`
- [ ] `bool`: `must`, `filter`, `should`, `must_not` (query vs filter context)
- [ ] Sorting, pagination (`from`/`size`), later `search_after`
- [ ] Highlighting, fuzzy search
- [ ] Queries built as Java classes (`ObjectNode`) → search REST endpoint
- [ ] Reading responses with `ObjectMapper` (`JsonNode` → hits, aggregations)

## Phase 7 – Aggregations

- [ ] `terms` aggregation
- [ ] Nested aggregations (e.g. level per service)
- [ ] `filter` / `filters` aggregations
- [ ] `date_histogram`
- [ ] Metrics: `avg`, `min`, `max`, `sum`, `percentiles`, `cardinality`
- [ ] Query + aggregations together, `size: 0`
- [ ] Stats REST endpoint

## Phase 8 – Index management

- [ ] Aliases (read / write alias)
- [ ] Index templates
- [ ] Time-based indices (`notifications-2026.10`)
- [ ] `_reindex` with zero downtime via alias switch
- [ ] `_update_by_query`, `_delete_by_query`

## Phase 9 – Quality

- [ ] Integration tests with Testcontainers (OpenSearch + Postgres)
- [ ] Log the JSON sent to OpenSearch so it can be pasted into Dev Tools

---

## Later (not now)

- [ ] Keycloak – securing the notifyhub REST endpoints