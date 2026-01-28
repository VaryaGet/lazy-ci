package io.github.varyaget.bot.cmd;

import java.io.File;
import java.util.List;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.repo.Client;
import io.github.varyaget.bot.repo.RepoClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public final class CmdListRepos implements Cmd<Update, TelegramClient> {

    private final Client repoClient;

    public CmdListRepos(final File directory) {
        this(new RepoClient(directory));
    }

    public CmdListRepos(final Client repoClient) {
        this.repoClient = repoClient;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                repoClient.repositories().isEmpty()
                    ? "No git repositories found"
                    : formatRepositoriesList(repoClient.repositories())
            )
        );
    }

    private String formatRepositoriesList(final List<File> repositories) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Git repositories:\n\n");
        
        for (int i = 0; i < repositories.size(); i++) {
            builder.append(i + 1)
                   .append(". ")
                   .append(repositories.get(i).getName())
                   .append("\n");
        }
        
        return builder.toString();
    }
}
