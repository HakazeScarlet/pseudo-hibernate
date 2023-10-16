import annotation_processors.IdProcessor;
import configuration.DataSourceProvider;

public class Main {

    public static void main(String[] args) {
        IdProcessor processor = new IdProcessor(new DataSourceProvider().create());
        processor.process(new Recipient());
    }
}