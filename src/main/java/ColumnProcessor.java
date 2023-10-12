import util.StringUtil;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ColumnProcessor {

    private final DataSource dataSource;

    public ColumnProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void process(Object object) throws SQLException {
        Class<?> objectClass = object.getClass();

        List<Field> annotatedFields = Arrays.stream(objectClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Column.class))
            .toList();

        objectClass.isAnnotationPresent(Table.class);
        String tableName = objectClass.getName();
        String convertedTableName = StringUtil.convertCamelCaseToSnakeCase(tableName);

        for (Field field : annotatedFields) {
            String name = field.getName();
            String convertedFieldName = StringUtil.convertCamelCaseToSnakeCase(name);

            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("ALTER TABLE " + convertedTableName + " ADD COLUMN " + convertedFieldName + " VARCHAR (50) ");
            statement.execute();
        }
    }
}



