import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {

    public DataSource create() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{"localhost"});
        dataSource.setPortNumbers(new int[]{5440});
        dataSource.setUser("postgres");
        dataSource.setPassword("admin");
        dataSource.setDatabaseName("recipients");
        return dataSource;
    }
}
