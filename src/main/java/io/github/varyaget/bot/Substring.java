package io.github.varyaget.bot;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extracts substring using regex patterns.
 *
 * @since 1.0
 */
public final class Substring implements Function<String, String> {
    /**
     * Pattern to match.
     */
    private final Pattern pattern;

    /**
     * Group to extract.
     */
    private final int group;

    /**
     * Constructor with regex string.
     *
     * @param regex Regex pattern
     */
    public Substring(final String regex) {
        this(regex, 1);
    }

    /**
     * Constructor with Pattern.
     *
     * @param pattern Pattern to use
     */
    public Substring(final Pattern pattern) {
        this(pattern, 1);
    }

    /**
     * Constructor with regex string and group.
     *
     * @param regex Regex pattern
     * @param group Group to extract
     */
    public Substring(final String regex, final int group) {
        this(Pattern.compile(regex), group);
    }

    /**
     * Constructor with Pattern and group.
     *
     * @param pattern Pattern to use
     * @param group   Group to extract
     */
    public Substring(final Pattern pattern, final int group) {
        this.pattern = pattern;
        this.group = group;
    }

    @Override
    public String apply(final String input) {
        final Matcher matcher = this.pattern.matcher(input);
        String result = "";
        if (matcher.find()) {
            result = matcher.group(this.group).trim();
        }
        return result;
    }
}
