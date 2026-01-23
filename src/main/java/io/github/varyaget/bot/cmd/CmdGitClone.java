package io.github.varyaget.bot.cmd;

import java.io.File;
import java.util.function.Function;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.Substring;
import io.github.varyaget.bot.git.GitClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CmdGitClone implements Cmd<Update, TelegramClient> {

    private final Function<String, String> pattern;
    private final File directory;
    private final GitClient gitClient;

    public CmdGitClone(String regex, File directory) {
        this(new Substring(regex), directory, new GitClient());
    }

    public CmdGitClone(Function<String,String> pattern, File directory) {
        this(pattern, directory, new GitClient());
    }

    public CmdGitClone(
        Function<String,String> pattern,
        File directory,
        GitClient gitClient
    ) {
        this.pattern = pattern;
        this.directory = directory;
        this.gitClient = gitClient;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        final String url = pattern.apply(update.getMessage().getText());
        gitClient.clone(url, directory);
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                "done"
            )
        );
    }
}
