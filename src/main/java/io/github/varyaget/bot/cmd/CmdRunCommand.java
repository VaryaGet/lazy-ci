package io.github.varyaget.bot.cmd;

import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.telegrambots.send.SendMessageWrap;
import io.github.varyaget.bot.TrimStart;
import java.util.function.Function;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Command to run shell command.
 *
 * @since 1.0
 */
public final class CmdRunCommand implements Cmd<Update, TelegramClient> {

    /**
     * Filter function.
     */
    private final Function<String, String> filter;

    /**
     * Constructor.
     *
     * @param trim Trim string
     */
    public CmdRunCommand(final String trim) {
        this(new TrimStart(trim));
    }

    /**
     * Constructor.
     *
     * @param filter Filter function
     */
    public CmdRunCommand(final Function<String, String> filter) {
        this.filter = filter;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        final String command = this.filter.apply(update.getMessage().getText());
        final Process process = new ProcessBuilder()
            .command("sh", "-c", command)
            .start();
        final int exitcode = process.waitFor();
        final String output = CmdRunCommand.readStream(process.getInputStream());
        final String error = CmdRunCommand.readStream(process.getErrorStream());
        final String response;
        if (exitcode == 0) {
            final String outtext;
            if (output.isEmpty()) {
                outtext = "(no output)";
            } else {
                outtext = output;
            }
            response = String.format(
                "Command executed successfully\nExit code: %d\nOutput: \n%s",
                exitcode,
                outtext
            );
        } else {
            final String errtext;
            if (error.isEmpty()) {
                errtext = "(no error output)";
            } else {
                errtext = error;
            }
            final String outtext;
            if (output.isEmpty()) {
                outtext = "(no output)";
            } else {
                outtext = output;
            }
            response = String.format(
                "Command failed\nExit code: %d\nError: %s\nOutput: %s",
                exitcode,
                errtext,
                outtext
            );
        }
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                response
            )
        );
    }

    private static String readStream(final java.io.InputStream stream) throws Exception {
        return new String(stream.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
    }
}
