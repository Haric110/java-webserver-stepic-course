package dbService.executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ResultHandler<T> {
    ArrayList<T> handle(ResultSet resultSet) throws SQLException;
}
