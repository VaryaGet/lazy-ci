package io.github.varyaget.bot.cmd;

import java.io.File;
import java.util.function.Function;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.TrimStart;
import io.github.varyaget.bot.git.Client;
import io.github.varyaget.bot.git.GitClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CmdGitClone implements Cmd<Update, TelegramClient> {

    private final Function<String, String> filter;
    private final Client gitClient;

    public CmdGitClone(String trim, File directory) {
        this(new TrimStart(trim), directory);
    }

    public CmdGitClone(Function<String, String> filter, File directory) {
        this(filter, new GitClient(directory));
    }

    public CmdGitClone(
        Function<String, String> filter,
        Client gitClient
    ) {
        this.filter = filter;
        this.gitClient = gitClient;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        this.gitClient.clone(filter.apply(update.getMessage().getText()));
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                "Git repository successfully pulled"
            )
        );
    }
}
