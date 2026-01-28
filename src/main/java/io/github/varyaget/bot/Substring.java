package io.github.varyaget.bot;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Substring implements Function<String, String> {
    private final Pattern pattern;
    private final int group;

    public Substring(String regex) {
        this(regex, 1);
    }

    public Substring(Pattern pattern) {
        this(pattern, 1);
    }

    public Substring(String regex, int group) {
        this(Pattern.compile(regex), group);
    }

    public Substring(Pattern pattern, int group) {
        this.pattern = pattern;
        this.group = group;
    }

    @Override
    public String apply(final String s) {
        if (pattern.matcher(s).find()) {
            return pattern.matcher(s).group(group).trim();
        }
        return "";
    }
}
