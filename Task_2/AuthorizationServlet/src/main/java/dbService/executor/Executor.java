package dbService.executor;

import dbService.dao.Exceptions.ArraysLengthsMismathException;
import dbService.dao.Exceptions.UnhandledArgumentTypeException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update) {
        try(Statement statement = connection.createStatement()) {
            statement.execute(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execUpdate(String update, int argsCount, @NotNull Class<?>[] argsTypes, @NotNull Object[] argsValues)
            throws ArraysLengthsMismathException {

        if (argsTypes.length != argsCount || argsValues.length != argsCount) {
            throw new ArraysLengthsMismathException("Count of passed argument types or does not match" +
                    "with count of passed objects. Or arrays lengths does not match with passed argsCount.");
        }

        try (PreparedStatement statement = connection.prepareStatement(update)) {
            for (int i = 0; i < argsCount; i++) {

                if (argsTypes[i] == Long.class) statement.setLong(i + 1, (long) argsValues[i]);
                else if (argsTypes[i] == String.class) statement.setString(i + 1, argsValues[i].toString());
                else throw new UnhandledArgumentTypeException(
                                "Unhandled argument type has been passed for update query \"" +
                                        update + "\"\scolumn: " + (i + 1) +
                                        "\s column type: " + argsTypes[i].getName());
            }

            statement.execute();
        } catch (SQLException | UnhandledArgumentTypeException e) {
            e.printStackTrace();
        }

    }

    /**
     * Вызывает Select-запрос.
     */
    public <T> ArrayList<T> execQuery(String query,
                                      int argsCount,
                                      Class<?>[] argsTypes,
                                      Object[] argsValues,
                                      @NotNull ResultHandler<T> handler)
            throws ArraysLengthsMismathException {
        ArrayList<T> values;

        if (argsTypes.length != argsCount || argsValues.length != argsCount) {
            throw new ArraysLengthsMismathException("Count of passed argument types or does not match" +
                    "with count of passed objects. Or arrays lengths does not match with passed argsCount.");
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < argsCount; i++) {
                if (argsTypes[i] == Long.class) statement.setLong(i + 1, (long) argsValues[i]);
                else if (argsTypes[i] == String.class) statement.setString(i + 1, argsValues[i].toString());
                else throw new UnhandledArgumentTypeException("Unhandled argument type has been passed for query \""
                            + query + "\"\scolumn: " + (i + 1)
                            + "\s column type: " + argsTypes[i].getName());
            }
            ResultSet rs = statement.executeQuery();
            values = handler.handle(rs);

            return values;
        } catch (SQLException | UnhandledArgumentTypeException e) {
            e.printStackTrace();
        }

        return null;
    }
}
