package annotation_processors;

import annotations.Id;
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
            String converted = StringUtil.convertCamelCaseToSnakeCase(name);

            // TODO: add table name
            try {
                PreparedStatement statement = connection.prepareStatement(
                    " ADD COLUMN " + converted + TypeConverter.getType(field.getType())
                );
                statement.execute();
            } catch (SQLException e) {
                throw new PreparedStatementException("Invalid database request");
            }
        } else {
            throw new MostThanOneIdField("Most Than One Id has been detected in class " + objectClass);
        }
    }

    private static final class ConnectionException extends RuntimeException {

        public ConnectionException(String message) {
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