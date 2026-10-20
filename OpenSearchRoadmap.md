# notifyhub – OpenSearch Learning Roadmap

Focus: OpenSearch. Spring Boot is the vehicle, not the goal.
Each checkbox can become its own branch.

Stack (mirrors work): OpenSearch, OpenSearch Dashboards + Dev Tools, Spring Boot, Lombok, UUID,
REST client + JSON, Java classes that build mappings, Postgres for index metadata.

---

## Phase 0 – Setup ✅

- [x] OpenSearch + OpenSearch Dashboards running locally (Docker Compose)
- [x] Postgres in the same Docker Compose
- [x] New module `notifyhub-search`

## Phase 1 – OpenSearch basics in Dev Tools (no Java yet) ✅

- [x] Index vs indices, documents, `_id`
- [x] Create / get / delete an index
- [x] Mappings: `text` vs `keyword`, `date`, numbers, multi-fields (`message.keyword`)
- [x] Index settings: shards, replicas (yellow vs green)
- [x] Index / get / update / delete documents (`noop`, `dynamic: strict`)
- [x] `_bulk` (incl. partial failures, `errors: true`)
- [x] `match` vs `term`, `operator`, `_analyze`
- [x] Why a mapping can't be changed; adding fields/sub-fields + `_update_by_query`
- [x] Alias, `_reindex` v1 → v2, atomic alias switch, rollback
- [ ] Analyzers (custom analyzer) – revisit in Phase 6

## Phase 2 – Talking to OpenSearch from Spring Boot ✅

- [x] REST client configured via `@Configuration` / `@Bean` + `application.properties`
- [x] Sending and receiving JSON (Jackson 3 `ObjectMapper` / `JsonNode` + Lombok classes)
- [x] Error handling: 404, 400 mapping errors, connection failures (`OpenSearchException`, `fail-on-startup`)
- [x] UUID as document `_id`

## Phase 3 – Mappings as Java classes

- [x] Java classes that build the mapping JSON with Jackson `ObjectMapper` + `ObjectNode` (`MappingBuilder`)
- [x] Index-creation service that applies mappings on startup (`NotificationIndexInitializer`)
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

Two different reasons for a new index – both handled with aliases:

- **Versioning**: the mapping changes → new index, reindex, switch the alias
- **Rollover** (used at work): same mapping, index too big/old → alias spans several indices, writes go to the newest

- [ ] Alias switch from Java (Phase 1 did it by hand)
- [ ] Zero-downtime reindex incl. writes that arrive during the copy
- [ ] Index templates (`notifications-*` gets the mapping automatically)
- [ ] Rollover by hand: `POST <alias>/_rollover`, conditions (`max_age`, `max_docs`, `max_size`), `-000001` naming
- [ ] One alias over several indices: search all, write to `is_write_index`
- [ ] ISM (Index State Management) policy: automatic rollover + delete old indices
- [ ] `_update_by_query`, `_delete_by_query`

## Phase 9 – Quality

- [ ] Integration tests with Testcontainers (OpenSearch + Postgres)
- [ ] Log the JSON sent to OpenSearch so it can be pasted into Dev Tools

---

## Later (not now)

- [ ] Keycloak – securing the notifyhub REST endpoints