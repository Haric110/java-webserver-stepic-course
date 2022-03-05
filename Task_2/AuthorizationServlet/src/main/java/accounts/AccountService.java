package accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static final Map<String, UserProfile> loginToProfile = new HashMap<>();
    private static final Map<String, UserProfile> sessionIdToProfile = new HashMap<>();

    /*private AccountService() {}

    public static AccountService getInstance() {
        if (instance == null) instance = new AccountService();
        return instance;
    }

    private static AccountService instance;*/

    public static void signUp(String login, UserProfile userProfile) {
        loginToProfile.put(login, userProfile);
    }

    public static void signIn(String userSessionId, UserProfile userProfile) {
        sessionIdToProfile.put(userSessionId, userProfile);
    }

    public static void signOut(String userSessionId) {
        sessionIdToProfile.remove(userSessionId);
    }

    public static UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBuSessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }
}
