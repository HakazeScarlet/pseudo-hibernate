package configuration;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

// TODO: extract values to app.properties
public class DataSourceProvider {

    public DataSource create() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{"localhost"});
        dataSource.setPortNumbers(new int[]{5434});
        dataSource.setUser("postgres");
        dataSource.setPassword("admin");
        dataSource.setDatabaseName("test_db");
        return dataSource;
    }
}
