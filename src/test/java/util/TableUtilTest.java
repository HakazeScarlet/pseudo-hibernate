package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TableUtilTest {

    @Test
    void whenClassHasName_returnPgTableName() {
        String actual = TableUtil.getTableName(ItemWithTableAnnotation.class);
        assertEquals("item_with_table_annotation", actual);
    }

    @Test
    void whenTableAnnotationIsNotPresent_throwTableIsNotPresentException() {
        assertThrows(
            TableUtil.IncorrectObjectClassException.class,
            () -> TableUtil.getTableName(ItemWithoutTableAnnotation.class)
        );
    }

    @Test
    void whenObjectClassIsNull_throwObjectClassIsNull() {
        assertThrows(
            TableUtil.IncorrectObjectClassException.class,
            () -> TableUtil.getTableName(null)
        );
    }
}