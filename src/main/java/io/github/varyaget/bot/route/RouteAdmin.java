package io.github.varyaget.bot.route;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.route.Route;
import io.github.artemget.teleroute.update.Wrap;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public final class RouteAdmin implements Route<Update, TelegramClient> {

    private final Route<Update, TelegramClient> origin;

    private final Set<Long> whitelist;

    public RouteAdmin(List<String> whitelist, final Route<Update, TelegramClient> origin) {
        this(whitelist.stream().map(Long::parseLong).collect(Collectors.toSet()), origin);
    }

    public RouteAdmin(final Set<Long> whitelist, final Route<Update, TelegramClient> origin) {
        this.origin = origin;
        this.whitelist = whitelist;
    }

    @Override
    public Optional<Cmd<Update, TelegramClient>> route(final Wrap<Update> wrap) {
        return whitelist.contains(wrap.src().getMessage().getFrom().getId()) ?
            origin.route(wrap) :
            Optional.empty();
    }
}
