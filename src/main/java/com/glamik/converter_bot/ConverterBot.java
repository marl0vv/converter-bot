package com.glamik.converter_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

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

    public Ability greetUser() {
        return Ability
                .builder()
                .name("start")
                .info("Greets user at the start of the bot's work")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Welcome to the WebpConverterBot! Send the image you want to convert.", ctx.chatId()))
                .build();
    }

    public Reply sayYuckOnImage() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> silent.send("Yuck", getChatId(upd));

        return Reply.of(action, Flag.PHOTO);
    }
}
