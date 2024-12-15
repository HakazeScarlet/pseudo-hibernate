package util;

import annotation.Table;

public final class TableUtil {

    private TableUtil() {
        // hide public constructor
    }

    public static String getTableName(Class<?> objectClass) {
        if (objectClass != null && objectClass.isAnnotationPresent(Table.class)) {
            String tableName = objectClass.getSimpleName();
            return StringUtil.convertCamelCaseToSnakeCase(tableName);
        }
        throw new IncorrectObjectClassException("Parameter objectClass is null or not annotated: " + objectClass);
    }

    public static final class IncorrectObjectClassException extends RuntimeException {

        public IncorrectObjectClassException(String message) {
            super(message);
        }
    }
}
