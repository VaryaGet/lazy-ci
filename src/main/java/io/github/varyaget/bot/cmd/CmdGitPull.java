package io.github.varyaget.bot.cmd;

import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.TrimStart;
import io.github.varyaget.bot.git.Client;
import io.github.varyaget.bot.git.GitClient;
import java.io.File;
import java.util.function.Function;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Command to pull git repository.
 *
 * @since 1.0
 */
public final class CmdGitPull implements Cmd<Update, TelegramClient> {

    /**
     * Filter function.
     */
    private final Function<String, String> filter;

    /**
     * Git client.
     */
    private final Client gitclient;

    /**
     * Constructor.
     *
     * @param trim      Trim string
     * @param directory Directory
     */
    public CmdGitPull(final String trim, final File directory) {
        this(new TrimStart(trim), new GitClient(directory));
    }

    /**
     * Constructor.
     *
     * @param filter    Filter function
     * @param directory Directory
     */
    public CmdGitPull(final Function<String, String> filter, final File directory) {
        this(filter, new GitClient(directory));
    }

    /**
     * Constructor.
     *
     * @param filter    Filter function
     * @param gitclient Git client
     */
    public CmdGitPull(
        final Function<String, String> filter,
        final Client gitclient
    ) {
        this.filter = filter;
        this.gitclient = gitclient;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        final String reponame = this.filter.apply(update.getMessage().getText());
        this.gitclient.pull(reponame);
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                String.format("Git repository '%s' successfully pulled", reponame)
            )
        );
    }
}
