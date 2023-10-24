import annotation_processors.ColumnProcessor;
import annotation_processors.IdProcessor;
import annotation_processors.TableProcessor;
import configuration.DataSourceProvider;

public class Main {

    public static void main(String[] args) {
        TableProcessor tableProcessor = new TableProcessor(new DataSourceProvider().create());
        tableProcessor.process(new Recipient());

        IdProcessor idProcessor = new IdProcessor(new DataSourceProvider().create());
        idProcessor.process(new Recipient());

        ColumnProcessor columnProcessor = new ColumnProcessor(new DataSourceProvider().create());
        columnProcessor.process(new Recipient());
    }
}