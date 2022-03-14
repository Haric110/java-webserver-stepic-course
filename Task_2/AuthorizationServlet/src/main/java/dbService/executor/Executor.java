package dbService.executor;

import dbService.dao.Exceptions.UnhandledArgumentTypeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("ClassCanBeRecord")
public final class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public boolean execUpdate(String update) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(update);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean execUpdate(String update, Object @NotNull [] argsValues) {
        try (PreparedStatement statement = connection.prepareStatement(update)) {
            setValuesToStatement(argsValues, statement, update);
            statement.execute();
            return true;
        } catch (SQLException | UnhandledArgumentTypeException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Вызывает Select-запрос.
     */
    public <T> @Nullable ArrayList<T> execQuery(String query,
                                                Object @NotNull [] argsValues,
                                                @NotNull ResultHandler<T> handler) {
        ArrayList<T> values;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setValuesToStatement(argsValues, statement, query);
            ResultSet rs = statement.executeQuery();
            values = handler.handle(rs);

            return values;
        } catch (SQLException | UnhandledArgumentTypeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> @Nullable ArrayList<T> execQuery(String query, @NotNull ResultHandler<T> handler) {
        ArrayList<T> values;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            values = handler.handle(rs);

            return values;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    private void setValuesToStatement(Object @NotNull [] argsValues,
                                      PreparedStatement statement,
                                      String query) throws SQLException, UnhandledArgumentTypeException {
        for (int i = 0; i < argsValues.length; i++) {
            if (argsValues[i] instanceof Long) statement.setLong(i + 1, (long) argsValues[i]);
            else if (argsValues[i] instanceof String) statement.setString(i + 1, argsValues[i].toString());
            else throw new UnhandledArgumentTypeException(
                        "Unhandled argument type has been passed for query \n\""
                                + query + "\"\ncolumn number: " + (i + 1));
        }
    }
}
