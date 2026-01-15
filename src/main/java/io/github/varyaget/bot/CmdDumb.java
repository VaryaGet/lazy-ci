package io.github.varyaget.bot;

import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CmdDumb implements Cmd<Update, TelegramClient> {

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                update.getMessage().getText()
            )
        );
    }
}
