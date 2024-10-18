package com.glamik.converter_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

@Component
public class ConverterBot extends AbilityBot {


    public ConverterBot(@Value("${bot.token}") String botToken) {
        super(botToken, "WebpConversionBot");
    }

    @Override
    public long creatorId() {
        return 398677200;
    }

    public Ability sayHelloWorld() {
        return Ability
                .builder()
                .name("hello")
                .info("Says \"Hello, World!\"")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello, world!", ctx.chatId()))
                .build();
    }
}
