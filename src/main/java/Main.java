public class Main {

    public static void main(String[] args) {

//        TableProcessor processor = new TableProcessor(new DataSourceProvider().create());
//        processor.process(new Recipient());

        ColumnProcessor processor = new ColumnProcessor(new DataSourceProvider().create());
        processor.process(new Recipient());
    }
}