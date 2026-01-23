package io.github.varyaget;

import java.io.File;
import io.github.artemget.entrys.file.EVal;
import io.github.artemget.teleroute.match.MatchRegex;
import io.github.artemget.teleroute.route.RouteFork;
import io.github.artemget.teleroute.telegrambots.bot.ConnectionTg;
import io.github.varyaget.bot.cmd.CmdClone;

public class Main {
    public static void main(String[] args) throws Exception {
        String props = "src/main/resources/application.local.yaml";
        File appsdir = new File(new EVal("bot.apps.dir", props).value());
        
        new ConnectionTg(
            new EVal("bot.token", props).value(),
            new RouteFork<>(
                new MatchRegex<>("clone .*"),
                new CmdClone("http.*", appsdir)
            )
        ).open();
    }
}
