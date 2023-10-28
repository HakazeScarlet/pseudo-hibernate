package annotation_processor;

import exception.DBConnectionException;
import exception.DBRequestException;
import util.StringUtil;
import util.TableUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableProcessor {

    private final Connection connection;

    public TableProcessor(DataSource dataSource) {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DBConnectionException("Connect to the database is failed");
        }
    }

    public void process(Object object) {
        Class<?> objectClass = object.getClass();
        String convertedTableName = TableUtil.getTableName(objectClass);
        try {
            String sqlQuery = "CREATE TABLE" + StringUtil.SPACE + convertedTableName + "()";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.execute();
        } catch (SQLException e) {
            throw new DBRequestException("Invalid database request");
        }
    }
}
