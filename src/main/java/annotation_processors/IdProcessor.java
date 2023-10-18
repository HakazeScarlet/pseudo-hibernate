package annotation_processors;

import annotations.Id;
import annotations.Table;
import util.StringUtil;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class IdProcessor {

    private final Connection connection;

    public IdProcessor(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new ConnectionException("Connect to the database is failed");
        }
    }

    public void process(Object object) {
        Class<?> objectClass = object.getClass();

        Field[] fields = objectClass.getDeclaredFields();
        List<Field> ids = Arrays.stream(fields)
            .filter(field -> field.isAnnotationPresent(Id.class))
            .toList();

        int size = ids.size();
        if (size == 1) {
            Field field = ids.get(0);
            String name = field.getName();
            String convertedIdField = StringUtil.convertCamelCaseToSnakeCase(name);

            try {
                PreparedStatement statement = connection.prepareStatement(
                    " ALTER TABLE " +
                        getTableName(objectClass) +
                        " ADD COLUMN " +
                        convertedIdField +
                        TypeConverter.getType(field.getType()) +
                        " SERIAL PRIMARY KEY "
                );
                statement.execute();
            } catch (SQLException e) {
                throw new PreparedStatementException("Invalid database request");
            }
        } else {
            throw new MostThanOneIdField("Most Than One Id has been detected in class " + objectClass);
        }
    }

    private String getTableName(Class<?> objectClass) {
        if (objectClass.isAnnotationPresent(Table.class)) {
            String tableName = objectClass.getName();
            return StringUtil.convertCamelCaseToSnakeCase(tableName);
        }
        throw new TableIsNotPresentException("The " + objectClass.getName() + " isn't a table");
    }

    private static final class ConnectionException extends RuntimeException {

        public ConnectionException(String message) {
            super(message);
        }
    }

    private static final class TableIsNotPresentException extends RuntimeException {

        public TableIsNotPresentException(String message) {
            super(message);
        }
    }

    private static final class PreparedStatementException extends RuntimeException {

        public PreparedStatementException(String message) {
            super(message);
        }
    }

    private static final class MostThanOneIdField extends RuntimeException {

        public MostThanOneIdField(String message) {
            super(message);
        }
    }
}