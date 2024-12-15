package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StringUtilTest {

    @Test
    void whenCamelCaseString_returnSnakeCaseString() {
        String actual = StringUtil.convertCamelCaseToSnakeCase("CamelString");
        assertEquals("camel_string", actual);
    }

    @Test
    void whenStringIsEmpty_returnEmptyString() {
        String actual = StringUtil.convertCamelCaseToSnakeCase("");
        assertEquals("", actual);
    }

    @Test
    void whenStringIsNull_throwNullStringException() {
        assertThrows(
            StringUtil.NullStringException.class,
            () -> StringUtil.convertCamelCaseToSnakeCase(null)
        );
    }

    @Test
    void whenSnakeCaseString_returnSnakeCaseString() {
        String actual = StringUtil.convertCamelCaseToSnakeCase("snake_case");
        assertEquals("snake_case", actual);
    }

    @Test
    void whenNoCaseString_returnSameString() {
        String actual = StringUtil.convertCamelCaseToSnakeCase("somethingstring");
        assertEquals("somethingstring", actual);
    }
}