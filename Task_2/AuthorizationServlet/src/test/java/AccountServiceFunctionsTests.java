import accounts.AccountService;
import accounts.UserProfile;
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
}
