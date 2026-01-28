package io.github.varyaget.bot;

import java.util.function.Function;

public class TrimStart implements Function<String, String> {
    private final String prefix;
    private final int prefixLength;

    public TrimStart(String prefix) {
        this.prefix = prefix;
        this.prefixLength = prefix.length();
    }

    @Override
    public String apply(final String s) {
        if (s.startsWith(prefix)) {
            return s.substring(prefixLength).trim();
        }
        return "";
    }
}
