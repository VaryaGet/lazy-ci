package io.github.varyaget.bot;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substring implements Function<String, String> {
    private final Pattern pattern;

    public Substring(String regex) {
        pattern = Pattern.compile(regex);
    }

    public Substring(final Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String apply(final String s) {
        final Matcher matcher = this.pattern.matcher(s);
        matcher.find();
        return matcher.group();
    }
}
