package annotation_processor;

import annotation.Id;
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

public class IdProcessor {

    private static final int ID_FIELD_COUNT = 1;

    private final Connection connection;

    public IdProcessor(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBConnectionException("Connect to the database is failed");
        }
    }

    public void process(Object object) {
        Class<?> objectClass = object.getClass();

        Field[] fields = objectClass.getDeclaredFields();
        List<Field> ids = Arrays.stream(fields)
            .filter(field -> field.isAnnotationPresent(Id.class))
            .toList();

        int size = ids.size();
        if (size == ID_FIELD_COUNT) {
            Field field = ids.get(0);
            String name = field.getName();
            String convertedIdField = StringUtil.convertCamelCaseToSnakeCase(name);

            // TODO: fix query
            try {
                String query = "ALTER TABLE " +
                    getTableName(objectClass) +
                    " ADD COLUMN " +
                    convertedIdField +
                    " " +
                    TypeConverter.getType(field.getType()) +
                    " CONSTRAINT " +
                    convertedIdField +
                    "_pk" +
                    " PRIMARY KEY " +
                    "(" + convertedIdField + ");";

                PreparedStatement statement = connection.prepareStatement(query);
                statement.execute();
            } catch (SQLException e) {
                throw new DBRequestException("Invalid database request");
            }
        } else {
            throw new MostThanOneIdField("Most Than One Id has been detected in class " + objectClass);
        }
    }

    private static final class MostThanOneIdField extends RuntimeException {

        public MostThanOneIdField(String message) {
            super(message);
        }
    }
}