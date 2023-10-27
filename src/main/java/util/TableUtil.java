package util;

import annotation.Table;

public final class TableUtil {

    private TableUtil() {
        // hide public constructor
    }

    public static String getTableName(Class<?> objectClass) {
        if (objectClass.isAnnotationPresent(Table.class)) {
            String tableName = objectClass.getName();
            return StringUtil.convertCamelCaseToSnakeCase(tableName);
        }
        throw new TableIsNotPresentException("The " + objectClass.getName() + " isn't a table");
    }

    private static final class TableIsNotPresentException extends RuntimeException {

        public TableIsNotPresentException(String message) {
            super(message);
        }
    }
}
