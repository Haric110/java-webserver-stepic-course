package accounts;

import dbService.DBService;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final Map<String, UsersDataSet> sessionIdToProfile = new HashMap<>();

    private static final Connection connection = DBService.getInstance().getConnection();
    private static final Executor executor = new Executor(connection);

    public static boolean signUp(String login, String password) {
        if (getUserByLogin(login) == null) {
            return new UsersDAO(executor).insertNewUser(login, password);
        }
        return false;
    }

    public static boolean signIn(String userSessionId, String login, String password) {
        UsersDataSet userProfile = getUserByLogin(login);

        if (userProfile != null && userProfile.getPassword().equals(password)
                && getUserBySessionId(userSessionId) == null) {
            sessionIdToProfile.put(userSessionId, userProfile);
            return true;
        }
        return false;
    }

    public static boolean signOut(String userSessionId) {
        if (getUserBySessionId(userSessionId) != null) {
            sessionIdToProfile.remove(userSessionId);
            return true;
        }
        return false;
    }

    public static UsersDataSet getUserByLogin(String login) {
        DBService.getInstance().checkConnection();
        return new UsersDAO(executor).getUserByLogin(login);
    }

    public static UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }
}
