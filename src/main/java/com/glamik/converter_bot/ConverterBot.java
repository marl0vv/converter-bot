package com.glamik.converter_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.function.BiConsumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.ALL;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class ConverterBot extends AbilityBot implements SpringLongPollingBot {

    private final String botToken;

    public ConverterBot(TelegramClient telegramClient,
                        @Value("${bot.username}") String botUsername,
                        @Value("${bot.token}") String botToken) {
        super(telegramClient, botUsername);
        this.botToken = botToken;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public long creatorId() {
        return 398677200;
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        this.onRegister();
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    public Ability sayHelloWorld() {
        return Ability.builder().name("hello").info("Says \"Hello, World!\"").locality(ALL).privacy(PUBLIC).action(ctx -> silent.send("Hello, world!", ctx.chatId())).build();
    }

    public Ability greetUser() {
        return Ability.builder().name("start").info("Greets user at the start of the bot's work").locality(ALL).privacy(PUBLIC).action(ctx -> silent.send("Welcome to the WebpConverterBot! Send the image you want to convert.", ctx.chatId())).build();
    }

    public Reply sayYuckOnImage() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> silent.send("Yuck", getChatId(upd));

        return Reply.of(action, Flag.PHOTO);
    }

}
