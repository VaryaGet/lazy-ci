package io.github.varyaget.bot;

import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class LazyBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient client;

    public LazyBot(String token){
        this(new OkHttpTelegramClient(token));
    }

    public LazyBot(TelegramClient client){
        this.client = client;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(), update.getMessage().getText());
            try {
                this.client.execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
