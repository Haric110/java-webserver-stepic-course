package dbService;

import org.postgresql.Driver;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBService {
    private Connection connection;

    private static DBService instance;

    public static DBService getInstance() {
        if (instance == null) instance = new DBService();
        return instance;
    }

    private DBService() {
        initConnection();
    }


    private void initConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.postgresql.Driver")
                    .getDeclaredConstructor().newInstance());

            final String
                    host = "localhost",
                    port = "5432",
                    database = "test_db",
                    url = "jdbc:postgresql://" +
                            host + ":" +
                            port + "/" +
                            database;

            Properties props = new Properties();
            props.setProperty("user", "test");
            props.setProperty("password", "test");
            props.setProperty("options", "-c statement_timeout=5min");

            connection = DriverManager.getConnection(url, props);

            System.out.println(connection.getClientInfo());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException
                | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void checkConnection() {
        if (this.connection == null) initConnection();
    }

    public Connection getConnection() {
        return this.connection;
    }
}
