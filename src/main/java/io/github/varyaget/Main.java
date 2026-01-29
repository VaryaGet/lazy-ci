package io.github.varyaget;

import java.io.File;
import io.github.artemget.entrys.file.EVal;
import io.github.artemget.entrys.operation.ESplit;
import io.github.artemget.teleroute.command.CmdBatch;
import io.github.artemget.teleroute.match.MatchRegex;
import io.github.artemget.teleroute.route.RouteDfs;
import io.github.artemget.teleroute.route.RouteFork;
import io.github.artemget.teleroute.telegrambots.bot.ConnectionTg;
import io.github.varyaget.bot.Substring;
import io.github.varyaget.bot.cmd.*;
import io.github.varyaget.bot.route.RouteAdmin;

public class Main {
    public static void main(String[] args) throws Exception {
        String props = "src/main/resources/application.local.yaml";
        File appsdir = new File(new EVal("bot.apps.dir", props).value());

        new ConnectionTg(
            new EVal("bot.token", props).value(),
            new RouteAdmin(
                new ESplit(new EVal("bot.whitelist", props)).value(),
                new RouteDfs<>(
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
            )
        ).open();
    }
}
