import dbService.DBService;
import dbService.dao.UsersDAO;
import dbService.dao.Exceptions.ArraysLengthsMismathException;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;
import org.junit.Assert;
import org.junit.Test;

public class DBServiceTests {
    @Test
    public void createTableTest() {
        UsersDAO dao = new UsersDAO(new Executor(DBService.getInstance().getConnection()));

        Assert.assertTrue(dao.createUsersTable());
    }

    @Test
    public void dropTableTest() {
        UsersDAO dao = new UsersDAO(new Executor(DBService.getInstance().getConnection()));

        Assert.assertTrue(dao.dropTable());
    }

    @Test
    public void insertNewUserTest() {
        UsersDAO dao = new UsersDAO(new Executor(DBService.getInstance().getConnection()));
        dao.dropTable();
        dao.createUsersTable();

        Assert.assertTrue(dao.insertNewUser("admin", "admin"));
    }

    @Test
    public void insertDuplicatedLogin() {
        UsersDAO dao = new UsersDAO(new Executor(DBService.getInstance().getConnection()));
        dao.dropTable();
        dao.createUsersTable();
        dao.insertNewUser("admin", "admin");

        Assert.assertFalse(dao.insertNewUser("admin", "admin"));
    }

    @Test
    public void selectUserByIdTest() throws ArraysLengthsMismathException {
        final String
                LOGIN1 = "TEST1",
                LOGIN2 = "TEST2",
                LOGIN3 = "TEST3",
                PSW1 = "PASS1",
                PSW2 = "PASS2",
                PSW3 = "PASS3";

        final UsersDataSet USR;

        UsersDAO dao = new UsersDAO(new Executor(DBService.getInstance().getConnection()));
        dao.dropTable();
        dao.createUsersTable();
        dao.insertNewUser(LOGIN1, PSW1);
        dao.insertNewUser(LOGIN2, PSW2);
        dao.insertNewUser(LOGIN3, PSW3);

        USR = dao.getUserById(2);
        assert USR != null;
        Assert.assertTrue(USR.getLogin().equals(LOGIN2) && USR.getPassword().equals(PSW2));
    }
}
