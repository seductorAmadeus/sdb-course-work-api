package operations;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHandler {

    private static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USERNAME = "martin";
    private static final String PASSWORD = "0797";

    public Connection createConnection() {
        Connection connection = null;

        System.out.print("Connecting to DB...");

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection == null) System.err.println("Couldn't create connection");
        }

        return connection;
    }

    public void close(Connection connection) {
        try {
            if (connection != null)
                System.out.println(" Successful operation!");
                connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
