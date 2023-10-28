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

import static util.StringUtil.SPACE;
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

            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("ALTER TABLE").append(SPACE)
                .append(getTableName(objectClass)).append(SPACE)
                .append("ADD COLUMN").append(SPACE)
                .append(convertedIdField).append(SPACE)
                .append(TypeConverter.getType(field.getType()))
                .append(",").append(SPACE)
                .append("ADD CONSTRAINT").append(SPACE)
                .append(convertedIdField)
                .append("_pk").append(SPACE)
                .append("PRIMARY KEY")
                .append("(").append(convertedIdField).append(")").append(";");

            try {
                PreparedStatement statement = connection.prepareStatement(sqlQuery.toString());
                statement.execute();
            } catch (SQLException e) {
                throw new DBRequestException("Invalid database request");
            }
        } else {
            throw new MoreThanOneIdField("More than one id has been detected in class " + objectClass);
        }
    }

    private static final class MoreThanOneIdField extends RuntimeException {

        public MoreThanOneIdField(String message) {
            super(message);
        }
    }
}