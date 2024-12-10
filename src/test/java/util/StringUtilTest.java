package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilTest {

    @Test
    void whenStringIsInCamelCase_returnStringInSnakeCase() {
        String expected = "camel_string";

        String actual = StringUtil.convertCamelCaseToSnakeCase("CamelString");

        assertEquals(expected, actual);
    }

    @Test
    void whenStringIsEmpty_returnEmptyString() {
        String actual = StringUtil.convertCamelCaseToSnakeCase("");
        assertEquals("", actual);
    }

    @Test
    void whenStringIsNull_throwNullPointerException() {
        assertThrows(
            StringUtil.NullStringException.class,
            () -> StringUtil.convertCamelCaseToSnakeCase(null)
        );
    }

    @Test
    void whenStringIsInSnakeCase_returnStringInSnakeCase() {
        String actual = StringUtil.convertCamelCaseToSnakeCase("snake_case");
        assertEquals("snake_case", actual);
    }

    @Test
    void whenStringHasRandomSymbolsInString_returnSameString() {
        String actual = StringUtil.convertCamelCaseToSnakeCase("somethingstring");
        assertEquals("somethingstring", actual);
    }
}