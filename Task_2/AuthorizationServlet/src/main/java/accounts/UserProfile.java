package accounts;

public record UserProfile(String password) {

    public String getPassword() {
        return password;
    }
}
