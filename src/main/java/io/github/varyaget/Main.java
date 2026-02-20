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

package io.github.varyaget;

import io.github.artemget.entrys.file.EVal;
import io.github.artemget.entrys.operation.ESplit;
import io.github.artemget.teleroute.command.CmdBatch;
import io.github.artemget.teleroute.match.MatchRegex;
import io.github.artemget.teleroute.route.RouteFork;
import io.github.artemget.teleroute.telegrambots.bot.ConnectionTg;
import io.github.varyaget.bot.Substring;
import io.github.varyaget.bot.cmd.CmdDockerUp;
import io.github.varyaget.bot.cmd.CmdGitClone;
import io.github.varyaget.bot.cmd.CmdGitPull;
import io.github.varyaget.bot.cmd.CmdListRepos;
import io.github.varyaget.bot.cmd.CmdRunCommand;
import io.github.varyaget.bot.route.RouteAdmin;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        String props = "src/main/resources/application.local.yaml";
        File appsdir = new File(new EVal("bot.apps.dir", props).value());

        new ConnectionTg(
            new EVal("bot.token", props).value(),
            new RouteAdmin(
                new ESplit(new EVal("bot.whitelist", props)).value(),
                new RouteFork<>(
                    new MatchRegex<>("ci .*"),
                    new RouteFork<>(
                        new MatchRegex<>("ci http.*"),
                        new CmdBatch<>(
                            new CmdGitClone("ci ", appsdir),
                            new CmdDockerUp(
                                new Substring("ci\\s+https?://[^/]+/[^/]+/([^\\s./]+)"),
                                appsdir
                            )
                        ),
                        new CmdBatch<>(
                            new CmdGitPull("ci ", appsdir),
                            new CmdDockerUp("ci ", appsdir)
                        )
                    )
                ),
                new RouteFork<>(
                    new MatchRegex<>("list"),
                    new CmdListRepos(appsdir)
                ),
                new RouteFork<>(
                    new MatchRegex<>("run .*"),
                    new CmdRunCommand("run ")
                )
            )
        ).open();
    }
}
