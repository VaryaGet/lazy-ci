package io.github.varyaget.bot.cmd;

import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.TrimStart;
import io.github.varyaget.bot.docker.Client;
import io.github.varyaget.bot.docker.DockerClient;
import java.io.File;
import java.util.function.Function;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Command to start docker containers.
 *
 * @since 1.0
 */
public final class CmdDockerUp implements Cmd<Update, TelegramClient> {

    /**
     * Filter function.
     */
    private final Function<String, String> filter;

    /**
     * Directory.
     */
    private final File directory;

    /**
     * Docker client.
     */
    private final Client dockerclient;

    /**
     * Constructor.
     *
     * @param trim      Trim string
     * @param directory Directory
     */
    public CmdDockerUp(final String trim, final File directory) {
        this(new TrimStart(trim), directory);
    }

    /**
     * Constructor.
     *
     * @param filter    Filter function
     * @param directory Directory
     */
    public CmdDockerUp(final Function<String, String> filter, final File directory) {
        this(filter, directory, new DockerClient());
    }

    /**
     * Constructor.
     *
     * @param regex        Regex string
     * @param directory    Directory
     * @param dockerclient Docker client
     */
    public CmdDockerUp(final String regex, final File directory, final Client dockerclient) {
        this(new TrimStart(regex), directory, dockerclient);
    }

    /**
     * Constructor.
     *
     * @param filter       Filter function
     * @param directory    Directory
     * @param dockerclient Docker client
     */
    public CmdDockerUp(
        final Function<String, String> filter,
        final File directory,
        final Client dockerclient
    ) {
        this.filter = filter;
        this.directory = directory;
        this.dockerclient = dockerclient;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        this.dockerclient.composeUp(
            new File(
                this.directory,
                this.filter.apply(update.getMessage().getText())
            )
        );
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                "Docker containers started successfully"
            )
        );
    }
}
