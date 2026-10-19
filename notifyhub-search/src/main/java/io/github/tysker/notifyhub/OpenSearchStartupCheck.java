package io.github.tysker.notifyhub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenSearchStartupCheck implements ApplicationRunner {

    private final RestClient openSearchRestClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String response = openSearchRestClient.get()
                .uri("/")
                .retrieve()
                .body(String.class);

        log.info("OpenSearch response: {}", response);

    }
}

