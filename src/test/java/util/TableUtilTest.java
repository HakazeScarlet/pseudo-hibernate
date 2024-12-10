package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TableUtilTest {

    @Test
    void whenClassHasName_returnPgTableName() {
        String actual = TableUtil.getTableName(ItemWithTableAnnotation.class);
        assertEquals("test_item", actual);
    }

    @Test
    void whenTableAnnotationIsNotPresent_throwTableIsNotPresentException() {
        assertThrows(
            TableUtil.TableIsNotPresentException.class,
            () -> TableUtil.getTableName(ItemWithoutTableAnnotation.class));
    }
}