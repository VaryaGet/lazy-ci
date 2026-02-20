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

import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Substring}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class SubstringTest {
    /**
     * Test regex pattern.
     */
    private static final String TEST_REGEX = "test(\\d+)";

    @Test
    void testConstructorWithStringRegex() {
        final Substring substring = new Substring(SubstringTest.TEST_REGEX);
        Assertions.assertNotNull(substring);
    }

    @Test
    void testConstructorWithPattern() {
        final Pattern pattern = Pattern.compile(SubstringTest.TEST_REGEX);
        final Substring substring = new Substring(pattern);
        Assertions.assertNotNull(substring);
    }

    @Test
    void testConstructorWithStringRegexAndGroup() {
        final Substring substring = new Substring(SubstringTest.TEST_REGEX, 1);
        Assertions.assertNotNull(substring);
    }

    @Test
    void testConstructorWithPatternAndGroup() {
        final Pattern pattern = Pattern.compile(SubstringTest.TEST_REGEX);
        final Substring substring = new Substring(pattern, 1);
        Assertions.assertNotNull(substring);
    }

    @Test
    void testApplyBasic() {
        final Substring substring = new Substring(SubstringTest.TEST_REGEX, 1);
        final String result = substring.apply("test123");
        Assertions.assertEquals("123", result, "Should extract matched group");
    }

    @Test
    void testApplyWithPattern() {
        final Pattern pattern = Pattern.compile(SubstringTest.TEST_REGEX);
        final Substring substring = new Substring(pattern, 1);
        final String result = substring.apply("test123");
        Assertions.assertEquals("123", result, "Should work with Pattern object");
    }

    @Test
    void testApplyWhenNoMatch() {
        final Substring substring = new Substring(SubstringTest.TEST_REGEX);
        final String result = substring.apply("no match here");
        Assertions.assertEquals("", result, "Should return empty string when no match");
    }

    @Test
    void testApplyWithGroupZero() {
        final Substring substring = new Substring(SubstringTest.TEST_REGEX, 0);
        final String result = substring.apply("test123");
        Assertions.assertEquals("test123", result.trim(), "Should return full match for group 0");
    }

    @Test
    void testApplyWithMultipleGroups() {
        final Substring substring = new Substring("(\\d+)-(\\d+)", 2);
        final String result = substring.apply("123-456");
        Assertions.assertEquals("456", result, "Should extract second group");
    }

    @Test
    void testApplyWithPrefixPattern() {
        final Substring substring = new Substring("prefix(\\d+)", 1);
        final String result = substring.apply("prefix123suffix");
        Assertions.assertEquals("123", result, "Should extract from middle of string");
    }

    @Test
    void testApplyWithNumberPattern() {
        final Substring substring = new Substring("(\\d+)", 1);
        final String result = substring.apply("abc123def");
        Assertions.assertEquals("123", result, "Should extract numbers from text");
    }
}
