package dbService.dataSets;


public class UsersDataSet {
    private final int userId;
    private final String login;
    private final String password;

    public UsersDataSet(int userId, String login, String password) {
        this.userId = userId;
        this.login = login;
        this.password = password;
    }

    public int getUserId() {
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
