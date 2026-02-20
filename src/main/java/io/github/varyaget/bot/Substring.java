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
