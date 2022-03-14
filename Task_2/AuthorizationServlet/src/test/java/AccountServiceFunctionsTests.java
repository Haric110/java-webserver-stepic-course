import accounts.AccountService;
import org.junit.Assert;
import org.junit.Test;

public class AccountServiceFunctionsTests {
    @Test
    public void unknownUserSignInTest() {
        Assert.assertFalse(AccountService.signIn("", "", ""));
    }

    @Test
    public void registeredUserSignInTest() {
        AccountService.signUp("test", "test");
        Assert.assertTrue(AccountService.signIn("", "test", "test"));
    }

    @Test
    public void passNullSessionIdTest() {
        Assert.assertNull(AccountService.getUserBySessionId(null));
    }

    @Test
    public void signOutWithNullProfile() {
        Assert.assertFalse(AccountService.signOut(null));
    }
}
