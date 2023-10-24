package annotation_processors;

import annotations.Table;
import exceptions.DBConnectionException;
import exceptions.DBRequestException;
import util.StringUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableProcessor {

    private final Connection connection;

    public TableProcessor(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBConnectionException("Connect to the database is failed");
        }
    }

    public void process(Object object) {
        Class<?> objectClass = object.getClass();

        if (objectClass.isAnnotationPresent(Table.class)) {
            String name = objectClass.getName();
            String convertedTableName = StringUtil.convertCamelCaseToSnakeCase(name);

            try {
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE " + convertedTableName + "()");
                statement.execute();
            } catch (SQLException e) {
                throw new DBRequestException("Invalid database request");
            }
        }
    }
}
