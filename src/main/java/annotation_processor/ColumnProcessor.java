package annotation_processor;

import annotation.Column;
import exception.DBConnectionException;
import exception.DBRequestException;
import util.StringUtil;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static util.TableUtil.getTableName;

public class ColumnProcessor {

    private final Connection connection;

    public ColumnProcessor(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBConnectionException("Connect to the database is failed");
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
                    "ALTER TABLE " + getTableName(objectClass) + " ADD COLUMN " + convertedFieldName + TypeConverter.getType(field.getType())
                );
                statement.execute();
            } catch(SQLException e) {
                throw new DBRequestException("Invalid database request");
            }
        }
    }

}



