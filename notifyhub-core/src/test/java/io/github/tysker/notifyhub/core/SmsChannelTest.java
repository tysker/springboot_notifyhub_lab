package io.github.tysker.notifyhub.core;

import io.github.tysker.notifyhub.core.config.NotifyHubConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

class SmsChannelTest {

    ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(SmsChannel.class);

    @Test
    void name() {
    }

    @Test
    void send() {
    }

    @Test
    void whenTheHavingValueIsSetToTrueThenTheBeanIsCreated() {
        contextRunner.withPropertyValues("notifyhub.sms.enabled=true")
                .withUserConfiguration(NotifyHubConfig.class)
                .run(context -> {
                    assert context.containsBean("smsChannel");
                });
    }

    @Test
    void whenTheHavingValueIsSetToFalseThenTheBeanIsNotCreated() {
        contextRunner.withPropertyValues("notifyhub.sms.enabled=false")
                .withUserConfiguration(NotifyHubConfig.class)
                .run(context -> {
                    assert !context.containsBean("smsChannel");
                });
    }
}