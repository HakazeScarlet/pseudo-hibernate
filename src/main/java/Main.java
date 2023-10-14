import annotation_processors.ColumnProcessor;
import properties.DataSourceProvider;

public class Main {

    public static void main(String[] args) {
        ColumnProcessor processor = new ColumnProcessor(new DataSourceProvider().create());
        processor.process(new Recipient());
    }
}