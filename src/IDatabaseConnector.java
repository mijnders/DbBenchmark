import java.sql.ResultSet;

/**
 * Build and manage the connection to a database.
 */
public interface IDatabaseConnector {
    /**
     * Connect to the database.
     * @return if the connection has benn successfully established.
     */
    public boolean connect();

    /**
     * Send a query to the database that returns
     * @param query that is send to the database.
     * @return the set of entries resulting from the query.
     */
    public ResultSet ask(String query);

    /**
     * Send a query to the database that does not return data, like creating new entries.
     * @param query that is send to the database.
     * @return if the query has been accepted.
     */
    public boolean send(String query);

    /**
     * Creates the structure and fills the database with given test data.
     * @param params is a list of strings that specify the structure of the database and the initial data. Specify which index is for which parameter.
     * @return the database has been successfully initialized.
     */
    public boolean initializeDatabase(String... params);
}
