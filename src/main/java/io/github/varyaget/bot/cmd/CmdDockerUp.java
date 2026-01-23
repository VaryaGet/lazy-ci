package io.github.varyaget.bot.cmd;

import java.io.File;
import java.util.function.Function;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.Substring;
import io.github.varyaget.bot.docker.Client;
import io.github.varyaget.bot.docker.DockerClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CmdDockerUp implements Cmd<Update, TelegramClient> {

    private final Function<String, String> pattern;
    private final File directory;
    private final Client dockerClient;

    public CmdDockerUp(String regex, File directory){
        this(new Substring(regex), directory);
    }

    public CmdDockerUp(Function<String, String> pattern, File directory){
        this(pattern,directory,new DockerClient());
    }

    public CmdDockerUp(String regex, File directory, Client dockerClient) {
        this(new Substring(regex), directory, dockerClient);
    }

    public CmdDockerUp(Function<String, String> pattern, File directory, Client dockerClient) {
        this.pattern = pattern;
        this.directory = directory;
        this.dockerClient = dockerClient;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        this.dockerClient.composeUp(
            new File(
                this.directory,
                pattern.apply(update.getMessage().getText())
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
