import util.StringUtil;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ColumnProcessor {

    private final DataSource dataSource;

    public ColumnProcessor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void process(Object object) {
        Class<?> objectClass = object.getClass();

        List<Field> annotatedFields = Arrays.stream(objectClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(Column.class))
            .toList();

        for (Field field : annotatedFields) {
            String name = field.getName();
            String convertedName = StringUtil.convertCamelCaseToSnakeCase(name);
        }
    }
}


