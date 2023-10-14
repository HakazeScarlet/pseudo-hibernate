package annotation_processors;

import annotations.Table;
import util.StringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableProcessor {

    private final DataSource dataSource;

    public TableProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void process(Object object) {
        Class<?> objectClass = object.getClass();

        if (objectClass.isAnnotationPresent(Table.class)) {
            String name = objectClass.getName();
            String convertedTableName = StringUtil.convertCamelCaseToSnakeCase(name);

            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE " + convertedTableName + "()");
                statement.execute();
            } catch (SQLException e) {
                throw new DatabaseQueryException("Connect to database is fail or invalid database request");
            }
        }
    }

    private static final class DatabaseQueryException extends RuntimeException {

        public DatabaseQueryException(String message) {
            super(message);
        }
    }
}
