package util;

public final class StringUtil {

    public static final String SPACE = " ";

    private StringUtil() {
        // hide public constructor
    }

    // TODO: add test
    public static String convertCamelCaseToSnakeCase(String str) {
        return str.replaceAll("\\B([A-Z])", "_$1").toLowerCase();
    }
}
