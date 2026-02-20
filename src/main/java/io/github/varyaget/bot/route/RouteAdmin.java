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

package io.github.varyaget.bot.route;

import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.route.Route;
import io.github.artemget.teleroute.route.RouteDfs;
import io.github.artemget.teleroute.update.Wrap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Admin route that filters by whitelist.
 *
 * @since 1.0
 */
public final class RouteAdmin implements Route<Update, TelegramClient> {
    /**
     * Origin route.
     */
    private final Route<Update, TelegramClient> origin;

    /**
     * Whitelist.
     */
    private final Set<Long> whitelist;

    /**
     * Constructor.
     *
     * @param whitelist Whitelist
     * @param routes    Routes
     */
    @SafeVarargs
    public RouteAdmin(
        final List<String> whitelist,
        final Route<Update, TelegramClient>... routes
    ) {
        this(whitelist, new RouteDfs<>(routes));
    }

    /**
     * Constructor.
     *
     * @param whitelist Whitelist
     * @param origin    Origin route
     */
    public RouteAdmin(
        final List<String> whitelist,
        final Route<Update, TelegramClient> origin
    ) {
        this(whitelist.stream().map(Long::parseLong).collect(Collectors.toSet()), origin);
    }

    /**
     * Constructor.
     *
     * @param whitelist Whitelist
     * @param origin    Origin route
     */
    public RouteAdmin(
        final Set<Long> whitelist,
        final Route<Update, TelegramClient> origin
    ) {
        this.origin = origin;
        this.whitelist = whitelist;
    }

    @Override
    public Optional<Cmd<Update, TelegramClient>> route(final Wrap<Update> wrap) {
        final Optional<Cmd<Update, TelegramClient>> result;
        if (this.whitelist.contains(wrap.src().getMessage().getFrom().getId())) {
            result = this.origin.route(wrap);
        } else {
            result = Optional.empty();
        }
        return result;
    }
}
