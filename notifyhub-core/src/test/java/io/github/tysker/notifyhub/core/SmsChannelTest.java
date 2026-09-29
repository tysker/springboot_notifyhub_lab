package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.core.config.NotifyHubConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class SmsChannelTest {

    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(SmsChannel.class);
    
    @Test
    void whenTheHavingValueIsSetToTrueThenTheBeanIsCreated() {
        contextRunner.withPropertyValues("notifyhub.sms.enabled=true")
                .withUserConfiguration(NotifyHubConfig.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(SmsChannel.class);
                });
    }

    @Test
    void whenTheHavingValueIsSetToFalseThenTheBeanIsNotCreated() {
        contextRunner.withPropertyValues("notifyhub.sms.enabled=false")
                .withUserConfiguration(NotifyHubConfig.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(SmsChannel.class);
                });
    }
}