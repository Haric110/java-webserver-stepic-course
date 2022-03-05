package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final Map<String, UserProfile> loginToProfile = new HashMap<>();
    private static final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

    public static boolean signUp(String login, String password) {
        if (getUserByLogin(login) == null) {
            loginToProfile.put(login, new UserProfile(login, password));
            return true;
        }
        return false;
    }

    public static boolean signIn(String userSessionId, String login, String password) {
        UserProfile userProfile = getUserByLogin(login);

        if (userProfile != null && userProfile.getPassword().equals(password)) {
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

    public static UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public static UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }
}
