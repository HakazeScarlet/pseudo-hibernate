import annotation_processors.TableProcessor;
import configuration.DataSourceProvider;

public class Main {

    public static void main(String[] args) {
        TableProcessor processor = new TableProcessor(new DataSourceProvider().create());
        processor.process(new Recipient());
    }
}