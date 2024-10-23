package com.glamik.converterbot;

import com.glamik.converterbot.feign.dto.ConversionTaskStatusDto;
import com.glamik.converterbot.exception.ConversionTaskNotFoundException;

import com.glamik.converterbot.feign.ConverterBotFeign;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Flag;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.UUID;
import java.util.function.BiConsumer;

import static org.telegram.telegrambots.abilitybots.api.objects.Locality.ALL;
import static org.telegram.telegrambots.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.telegrambots.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class ConverterBot extends AbilityBot implements SpringLongPollingBot {

    private final String botToken;
    private final long creatorId;

    private final ConverterBotFeign converterBotFeign;

    public ConverterBot(TelegramClient telegramClient,
                        @Value("${bot.username}") String botUsername,
                        @Value("${bot.token}") String botToken,
                        @Value("${bot.creatorId}") long creatorId,

                        ConverterBotFeign converterBotFeign) {
        super(telegramClient, botUsername);
        this.botToken = botToken;
        this.creatorId = creatorId;
        this.converterBotFeign = converterBotFeign;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public long creatorId() {
        return creatorId;
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
        return Ability.builder()
                .name("hello")
                .info("Says \"Hello, World!\"")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Hello, world!", ctx.chatId()))
                .build();
    }

    public Ability greetUser() {
        return Ability.builder()
                .name("start")
                .info("Greets user at the start of the bot's work")
                .locality(ALL)
                .privacy(PUBLIC)
                .action(ctx -> silent.send("Welcome to the WebpConverterBot! Send the image you want to convert.", ctx.chatId()))
                .build();
    }

    public Ability conversionStatus() {
        return Ability.builder()
                .name("status")
                .info("Prints the status of a conversion task")
                .locality(ALL)
                .privacy(PUBLIC)
                .input(1)
                .action(this::handleConversionStatus)
                .build();
    }

    private ConversionTaskStatusDto getStatus(UUID taskId) {
        ConversionTaskStatusDto status;
        try {
            status = converterBotFeign.getTaskStatus(taskId);
        } catch (FeignException.NotFound e) {
            throw new ConversionTaskNotFoundException("Conversion task with id " + taskId + " was not found");
        }
        return status;
    }

    private void handleConversionStatus(MessageContext ctx) {
        UUID taskId;
        try {
            taskId = UUID.fromString(ctx.firstArg());
        } catch (IllegalArgumentException e) {
            silent.send("Invalid task ID format. Prove a valid UUID.", ctx.chatId());
            return;
        }
        try {
            ConversionTaskStatusDto status = getStatus(taskId);
            switch (status.getStatus()) {
                case SUCCESS -> silent.send("Your image was successfully converted.", ctx.chatId());
                case ERROR -> {
                    silent.send("There was an error during conversion process!", ctx.chatId());
                    silent.send(String.valueOf(status.getErrorMessage()), ctx.chatId());
                }
                case DELETED ->
                        silent.send("Your image was old and has been deleted. Convert image again.", ctx.chatId());
                case PENDING -> silent.send("Your image conversion is in process. Please wait.", ctx.chatId());
                default -> silent.send("Unknown status.", ctx.chatId());
            }
        } catch (ConversionTaskNotFoundException e) {
            silent.send("Conversion task with ID " + taskId + " was not found.", ctx.chatId());
        }
    }


    public Reply sayYuckOnImage() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> silent.send("Yuck", getChatId(upd));

        return Reply.of(action, Flag.PHOTO);
    }

}
