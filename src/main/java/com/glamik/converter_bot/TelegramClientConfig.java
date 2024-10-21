package com.glamik.converter_bot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class TelegramClientConfig {
    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient("8128311763:AAFbethIcdK100Ici4SUypqKTlF98Zsykyw");
    }
}
