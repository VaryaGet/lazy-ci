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
import io.github.varyaget.bot.repo.Client;
import io.github.varyaget.bot.repo.RepoClient;
import java.io.File;
import java.util.List;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Command to list git repositories.
 *
 * @since 1.0
 */
public final class CmdListRepos implements Cmd<Update, TelegramClient> {

    /**
     * Repository client.
     */
    private final Client repo;

    /**
     * Constructor.
     *
     * @param directory Directory
     */
    public CmdListRepos(final File directory) {
        this(new RepoClient(directory));
    }

    /**
     * Constructor.
     *
     * @param repo Repository client
     */
    public CmdListRepos(final Client repo) {
        this.repo = repo;
    }

    @Override
    public Send<TelegramClient> execute(final Update update) throws Exception {
        final String message;
        if (this.repo.repositories().isEmpty()) {
            message = "No git repositories found";
        } else {
            message = CmdListRepos.formatRepositoriesList(this.repo.repositories());
        }
        return new SendMessageWrap<>(
            new SendMessage(
                update.getMessage().getChatId().toString(),
                message
            )
        );
    }

    private static String formatRepositoriesList(final List<File> repositories) {
        final StringBuilder builder = new StringBuilder(100);
        builder.append("Git repositories:\n\n");
        int idx = 0;
        for (final File repository : repositories) {
            builder.append(idx + 1)
                .append(". ")
                .append(repository.getName())
                .append('\n');
            idx = idx + 1;
        }
        return builder.toString();
    }
}
