package com.glamik.converter_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;

@Component
public class ConverterBot extends AbilityBot {


    public ConverterBot(@Value("${bot.token}") String botToken) {
        super(botToken, "WebpConversionBot");
    }

    @Override
    public long creatorId() {
        return 398677200;
    }


}
