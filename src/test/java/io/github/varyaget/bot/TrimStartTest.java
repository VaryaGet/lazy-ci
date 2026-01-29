package io.github.varyaget.bot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TrimStartTest {

    @Test
    void testConstructor() {
        TrimStart trimStart = new TrimStart("prefix");
        assertNotNull(trimStart);
    }

    @Test
    void testApplyBasic() {
        TrimStart trimStart = new TrimStart("prefix");
        String result = trimStart.apply("prefix some text");
        assertEquals("some text", result);
    }

    @Test
    void testApplyWhenNoPrefix() {
        TrimStart trimStart = new TrimStart("prefix");
        String result = trimStart.apply("no prefix here");
        assertEquals("", result);
    }

    @Test
    void testApplyWithEmptyString() {
        TrimStart trimStart = new TrimStart("prefix");
        String result = trimStart.apply("");
        assertEquals("", result);
    }

    @Test
    void testApplyWithOnlyPrefix() {
        TrimStart trimStart = new TrimStart("prefix");
        String result = trimStart.apply("prefix");
        assertEquals("", result);
    }

    @Test
    void testApplyWithPrefixAndWhitespace() {
        TrimStart trimStart = new TrimStart("prefix");
        String result = trimStart.apply("prefix   some text");
        assertEquals("some text", result);
    }

    @Test
    void testApplyWithPrefixAndNoWhitespace() {
        TrimStart trimStart = new TrimStart("prefix");
        String result = trimStart.apply("prefixsome text");
        assertEquals("some text", result);
    }

    @Test
    void testApplyWithCaseSensitivePrefix() {
        TrimStart trimStart = new TrimStart("Prefix");
        String result = trimStart.apply("prefix some text");
        assertEquals("", result);
    }

    @Test
    void testApplyWithSpecialCharactersPrefix() {
        TrimStart trimStart = new TrimStart("/command ");
        String result = trimStart.apply("/command some text");
        assertEquals("some text", result);
    }

    @Test
    void testApplyWithCommandPrefix() {
        TrimStart trimStart = new TrimStart("/command");
        String result = trimStart.apply("/command text");
        assertEquals("text", result);
    }

    @Test
    void testApplyWithNumberPrefix() {
        TrimStart trimStart = new TrimStart("123");
        String result = trimStart.apply("123numbers");
        assertEquals("numbers", result);
    }
}
