package dbService.dataSets;


public class UsersDataSet {
    private final long userId;
    private final String login;
    private final String password;

    public UsersDataSet(long userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "userId=" + userId +
                ", login='" + login +
                "', password='" + password +
                "'}";
    }
}
