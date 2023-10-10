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

    public void process(Object object) throws SQLException {
        Class<?> objectClass = object.getClass();

        if (objectClass.isAnnotationPresent(Table.class)) {
            String name = objectClass.getName();
            String convertedName = StringUtil.convertCamelCaseToSnakeCase(name);

            try (Connection connection = dataSource.getConnection()) {
                PreparedStatement statement = connection.prepareStatement("CREATE TABLE " + convertedName + "()");
                statement.execute();
            }
        }
    }
}
