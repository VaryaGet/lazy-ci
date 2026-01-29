package io.github.varyaget.bot;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SubstringTest {

    @Test
    void testConstructorWithStringRegex() {
        Substring substring = new Substring("test(\\d+)");
        assertNotNull(substring);
    }

    @Test
    void testConstructorWithPattern() {
        Pattern pattern = Pattern.compile("test(\\d+)");
        Substring substring = new Substring(pattern);
        assertNotNull(substring);
    }

    @Test
    void testConstructorWithStringRegexAndGroup() {
        Substring substring = new Substring("test(\\d+)", 1);
        assertNotNull(substring);
    }

    @Test
    void testConstructorWithPatternAndGroup() {
        Pattern pattern = Pattern.compile("test(\\d+)");
        Substring substring = new Substring(pattern, 1);
        assertNotNull(substring);
    }

    @Test
    void testApplyBasic() {
        Substring substring = new Substring("test(\\d+)", 1);
        String result = substring.apply("test123");
        assertEquals("123", result);
    }

    @Test
    void testApplyWithPattern() {
        Pattern pattern = Pattern.compile("test(\\d+)");
        Substring substring = new Substring(pattern, 1);
        String result = substring.apply("test123");
        assertEquals("123", result);
    }

    @Test
    void testApplyWhenNoMatch() {
        Substring substring = new Substring("test(\\d+)");
        String result = substring.apply("no match here");
        assertEquals("", result);
    }

    @Test
    void testApplyWithGroupZero() {
        Substring substring = new Substring("test(\\d+)", 0);
        String result = substring.apply("test123");
        assertEquals("test123", result.trim());
    }

    @Test
    void testApplyWithMultipleGroups() {
        Substring substring = new Substring("(\\d+)-(\\d+)", 2);
        String result = substring.apply("123-456");
        assertEquals("456", result);
    }

    @Test
    void testApplyWithPrefixPattern() {
        Substring substring = new Substring("prefix(\\d+)", 1);
        String result = substring.apply("prefix123suffix");
        assertEquals("123", result);
    }

    @Test
    void testApplyWithNumberPattern() {
        Substring substring = new Substring("(\\d+)", 1);
        String result = substring.apply("abc123def");
        assertEquals("123", result);
    }
}
