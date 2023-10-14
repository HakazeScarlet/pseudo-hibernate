package annotation_processors;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TypeConverter {

    STRING(String.class, "CHARACTER VARYING(64)"),
    INTEGER(Integer.class, "INTEGER"),
    LONG(Long.class, "SERIAL"),
    DOUBLE(Double.class, "DOUBLE PRECISIGION"),
    BOOLEAN(Boolean.class, "BOOLEAN"),
    ;

    private static final Map<Class<?>, String> types = initializeMap();
    private final Class<?> javaType;
    private final String pgType;

    TypeConverter(Class<?> javaType, String pgType) {
        this.javaType = javaType;
        this.pgType = pgType;
    }

    public static String getType(Class<?> javaType) {
        return types.get(javaType);
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public String getPgType() {
        return pgType;
    }

    private static Map<Class<?>, String> initializeMap() {
        return Arrays.stream(TypeConverter.values())
            .collect(Collectors.toMap(TypeConverter::getJavaType, TypeConverter::getPgType));
    }
}
