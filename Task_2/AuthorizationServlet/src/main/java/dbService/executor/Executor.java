package dbService.executor;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.ArrayList;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute(update);
        stmt.close();
    }

    /**
     * Вызывает Select-запрос.
     * */
    public <T> ArrayList<T> execQuery(String query, @NotNull ResultHandler<T> handler) {
        ArrayList<T> values;

        try(Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            values = handler.handle(resultSet);

            return values;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
