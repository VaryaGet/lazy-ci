package io.github.varyaget.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TrimStart}.
 *
 * @since 1.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class TrimStartTest {
    /**
     * Test prefix.
     */
    private static final String PREFIX = "prefix";

    @Test
    void testConstructor() {
        final TrimStart trim = new TrimStart(TrimStartTest.PREFIX);
        Assertions.assertNotNull(trim);
    }

    @Test
    void testApplyBasic() {
        final TrimStart trim = new TrimStart(TrimStartTest.PREFIX);
        final String result = trim.apply("prefix some text");
        Assertions.assertEquals("some text", result, "Should trim prefix and whitespace");
    }

    @Test
    void testApplyWhenNoPrefix() {
        final TrimStart trim = new TrimStart(TrimStartTest.PREFIX);
        final String result = trim.apply("no prefix here");
        Assertions.assertEquals("", result, "Should return empty string when no prefix");
    }

    @Test
    void testApplyWithEmptyString() {
        final TrimStart trim = new TrimStart(TrimStartTest.PREFIX);
        final String result = trim.apply("");
        Assertions.assertEquals("", result, "Should return empty string for empty input");
    }

    @Test
    void testApplyWithOnlyPrefix() {
        final TrimStart trim = new TrimStart(TrimStartTest.PREFIX);
        final String result = trim.apply(TrimStartTest.PREFIX);
        Assertions.assertEquals("", result, "Should return empty string when only prefix");
    }

    @Test
    void testApplyWithPrefixAndWhitespace() {
        final TrimStart trim = new TrimStart(TrimStartTest.PREFIX);
        final String result = trim.apply("prefix   some text");
        Assertions.assertEquals("some text", result, "Should trim prefix and multiple spaces");
    }

    @Test
    void testApplyWithPrefixAndNoWhitespace() {
        final TrimStart trim = new TrimStart(TrimStartTest.PREFIX);
        final String result = trim.apply("prefixsome text");
        Assertions.assertEquals("some text", result, "Should trim prefix without whitespace");
    }

    @Test
    void testApplyWithCaseSensitivePrefix() {
        final TrimStart trim = new TrimStart("Prefix");
        final String result = trim.apply("prefix some text");
        Assertions.assertEquals("", result, "Should be case sensitive");
    }

    @Test
    void testApplyWithSpecialCharactersPrefix() {
        final TrimStart trim = new TrimStart("/command ");
        final String result = trim.apply("/command some text");
        Assertions.assertEquals("some text", result, "Should handle special characters");
    }

    @Test
    void testApplyWithCommandPrefix() {
        final TrimStart trim = new TrimStart("/command");
        final String result = trim.apply("/command text");
        Assertions.assertEquals("text", result, "Should handle command prefix");
    }

    @Test
    void testApplyWithNumberPrefix() {
        final TrimStart trim = new TrimStart("123");
        final String result = trim.apply("123numbers");
        Assertions.assertEquals("numbers", result, "Should handle numeric prefix");
    }
}
