package io.github.varyaget;

import io.github.artemget.entrys.EntryException;
import io.github.artemget.entrys.file.EVal;
import io.github.varyaget.bot.LazyBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    static void main() {
        String props = "src/main/resources/application.local.yaml";
        try {
            String token = new EVal("bot-token", props).value();
            new TelegramBotsLongPollingApplication()
                    .registerBot(token, new LazyBot(token));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (EntryException e) {
            throw new RuntimeException(e);
        }
    }
}
