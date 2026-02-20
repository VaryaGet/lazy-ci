/*
 * MIT License
 *
 * Copyright (c) 2026 Varvara Getmanskaya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
