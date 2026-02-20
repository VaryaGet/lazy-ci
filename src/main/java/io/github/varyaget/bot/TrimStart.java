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
