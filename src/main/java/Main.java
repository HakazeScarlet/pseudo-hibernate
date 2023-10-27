import annotation_processor.ColumnProcessor;
import annotation_processor.IdProcessor;
import annotation_processor.TableProcessor;
import configuration.DataSourceProvider;

import javax.sql.DataSource;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSourceProvider().create();
        Recipient recipient = new Recipient();

        TableProcessor tableProcessor = new TableProcessor(dataSource);
        tableProcessor.process(recipient);

        IdProcessor idProcessor = new IdProcessor(dataSource);
        idProcessor.process(recipient);

        ColumnProcessor columnProcessor = new ColumnProcessor(dataSource);
        columnProcessor.process(recipient);
    }
}