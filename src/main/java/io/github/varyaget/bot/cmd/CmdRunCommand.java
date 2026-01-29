package io.github.varyaget.bot.cmd;

import java.util.function.Function;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.TrimStart;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class CmdRunCommand implements Cmd<Update, TelegramClient> {

    private final Function<String, String> filter;

    public CmdRunCommand(String trim) {
        this(new TrimStart(trim));
    }

    public CmdRunCommand(Function<String, String> filter) {
        this.filter = filter;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        System.out.println(update.getMessage().getFrom().getId());
        String command = filter.apply(update.getMessage().getText());

        Process process = new ProcessBuilder()
            .command("sh", "-c", command)
            .start();

        int exitCode = process.waitFor();
        String output = readStream(process.getInputStream());
        String error = readStream(process.getErrorStream());

        String response;
        if (exitCode == 0) {
            response = String.format(
                "Command executed successfully\nExit code: %d\nOutput: \n%s",
                exitCode,
                output.isEmpty() ? "(no output)" : output
            );
        } else {
            response = String.format(
                "Command failed\nExit code: %d\nError: %s\nOutput: %s",
                exitCode,
                error.isEmpty() ? "(no error output)" : error,
                output.isEmpty() ? "(no output)" : output
            );
        }

        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                response
            )
        );
    }

    private String readStream(java.io.InputStream stream) throws Exception {
        return new String(stream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
    }
}
