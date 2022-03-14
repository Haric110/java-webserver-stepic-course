import dbService.DBService;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

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
    public void selectUserByIdTest() {
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

    @Test
    public void selectUserByLoginTest() {
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

        USR = dao.getUserByLogin(LOGIN2);
        assert USR != null;
        Assert.assertTrue(USR.getLogin().equals(LOGIN2) && USR.getPassword().equals(PSW2));
    }

    @Test
    public void selectUserByUnknownLoginTest() {
        final String
                LOGIN1 = "TEST1",
                LOGIN2 = "TEST2",
                LOGIN3 = "TEST3",
                PSW1 = "PASS1",
                PSW2 = "PASS2",
                PSW3 = "PASS3";

        UsersDAO dao = new UsersDAO(new Executor(DBService.getInstance().getConnection()));
        dao.dropTable();
        dao.createUsersTable();
        dao.insertNewUser(LOGIN1, PSW1);
        dao.insertNewUser(LOGIN2, PSW2);
        dao.insertNewUser(LOGIN3, PSW3);

        Assert.assertNull(dao.getUserByLogin("UNKNOWN"));
    }

    @Test
    public void selectMultipleUsersTest() {
        final String
                LOGIN1 = "TEST1",
                LOGIN2 = "TEST2",
                LOGIN3 = "TEST3",
                PSW1 = "PASS1",
                PSW2 = "PASS2",
                PSW3 = "PASS3";

        UsersDAO dao = new UsersDAO(new Executor(DBService.getInstance().getConnection()));
        dao.dropTable();
        dao.createUsersTable();
        dao.insertNewUser(LOGIN1, PSW1);
        dao.insertNewUser(LOGIN2, PSW2);
        dao.insertNewUser(LOGIN3, PSW3);

        ArrayList<UsersDataSet> users = dao.getUsersByLikeCondition();

        for (UsersDataSet user : users) {
            System.out.println("id: " + user.getUserId());
            System.out.println("login: " + user.getLogin());
            System.out.println("password: " + user.getPassword());
        }

        Assert.assertEquals(3, users.size());
    }
}
