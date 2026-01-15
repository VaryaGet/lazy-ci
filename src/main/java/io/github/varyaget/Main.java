package io.github.varyaget;

import io.github.artemget.entrys.file.EVal;
import io.github.artemget.teleroute.route.RouteEnd;
import io.github.artemget.teleroute.telegrambots.bot.ConnectionTg;
import io.github.varyaget.bot.CmdDumb;

public class Main {
    public static void main(String[] args) throws Exception {
        String props = "src/main/resources/application.local.yaml";
        new ConnectionTg(
            new EVal("bot-token", props).value(),
            new RouteEnd<>(
                new CmdDumb()
            )
        ).open();
    }
}
