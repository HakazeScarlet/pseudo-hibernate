import util.StringUtil;

public class Main {
    public static void main(String[] args) {

//        ColumnProcessor processor = new ColumnProcessor(new DataSourceProvider().create());
//        processor.process(new Recipient());

        System.out.println(StringUtil.convertCamelCaseToSnakeCase("ILoveSweetBlackTea"));
        System.out.println(StringUtil.convertCamelCaseToSnakeCase("iLoveSweetBlackTea"));
    }
}