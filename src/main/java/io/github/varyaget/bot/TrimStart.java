package io.github.varyaget.bot;

import java.util.function.Function;

/**
 * Trims a specified prefix from strings.
 *
 * @since 1.0
 */
public final class TrimStart implements Function<String, String> {
    /**
     * Prefix to trim.
     */
    private final String prefix;

    /**
     * Constructor.
     *
     * @param prefix Prefix to trim
     */
    public TrimStart(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String apply(final String input) {
        String result = "";
        if (input.startsWith(this.prefix)) {
            result = input.substring(this.prefix.length()).trim();
        }
        return result;
    }
}
