package io.github.varyaget.bot.cmd;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.Substring;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.URIish;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CmdClone implements Cmd<Update, TelegramClient> {

    private final Function<String, String> pattern;
    private final File directory;

    public CmdClone(String regex, File directory) {
        this(new Substring(regex), directory);
    }

    public CmdClone(Function<String,String> pattern, File directory) {
        this.pattern = pattern;
        this.directory = directory;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        String url = pattern.apply(update.getMessage().getText());
        File dir = new File(this.directory,new URIish(url).getHumanishName());
        if (!this.directory.exists() && !this.directory.mkdirs()) {
            throw new IOException(
                String.format(
                    "Failed to create directory: %s",
                    this.directory.getAbsolutePath()
                )
            );
        }
        Git.cloneRepository()
            .setURI(url)
            .setDirectory(dir)
            .call();
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                "done"
            )
        );
    }
}
