import accounts.AccountService;
import accounts.UserProfile;
import dbService.dao.Exceptions.ArraysLengthsMismathException;
import org.junit.Assert;
import org.junit.Test;

public class AccountServiceFunctionsTests {
    @Test
    public void unknownUserSignInTest() throws ArraysLengthsMismathException {
        Assert.assertFalse(AccountService.signIn("", "", ""));
    }

    @Test
    public void registeredUserSignInTest() throws ArraysLengthsMismathException {
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
