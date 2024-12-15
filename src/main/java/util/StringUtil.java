package util;

public final class StringUtil {

    public static final String SPACE = "\\s";

    private StringUtil() {
        // hide public constructor
    }

    // TODO: add test
    public static String convertCamelCaseToSnakeCase(String str) {
        if (str == null) {
            throw new NullStringException("Converted string is null");
        }
        return str.replaceAll("\\B([A-Z])", "_$1").toLowerCase();
    }

    public static class NullStringException extends RuntimeException {

        public NullStringException(String message) {
            super(message);
        }
    }
}
