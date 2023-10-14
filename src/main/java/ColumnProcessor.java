import util.StringUtil;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ColumnProcessor {

    private final Connection connection;

    public ColumnProcessor(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("Connect to the database is failed");
        }
    }

    public void process(Object object) {
        Class<?> objectClass = object.getClass();

        List<Field> annotatedFields = Arrays.stream(objectClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Column.class))
            .toList();

        for (Field field : annotatedFields) {
            String name = field.getName();
            String convertedFieldName = StringUtil.convertCamelCaseToSnakeCase(name);

            try {
                PreparedStatement statement = connection.prepareStatement(
                    "ALTER TABLE " + getTableName(objectClass) + " ADD COLUMN " + convertedFieldName + " VARCHAR (50)"
                );
//                PreparedStatement statement = connection.prepareStatement(
//                    "ALTER TABLE " + getTableName(objectClass) + " ADD COLUMN " + convertedFieldName + mapType(field.getType())
//                );
                statement.execute();
            } catch(SQLException e) {
                throw new PrepareStatementException("Invalid database request");
            }
        }
    }

    private String mapType(Class<?> type) {
        // TODO: add types remapping (think about java enum)
//        type String -> "character varying(64)"
//        type int -> "integer"
        return null;
    }

    private String getTableName(Class<?> objectClass) {
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

    private static final class ConnectionException extends RuntimeException {

        public ConnectionException(String message) {
            super(message);
        }
    }

    private static final class PrepareStatementException extends RuntimeException {

        public PrepareStatementException(String message) {
            super(message);
        }
    }
}



