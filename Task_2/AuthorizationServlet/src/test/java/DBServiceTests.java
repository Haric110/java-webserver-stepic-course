import dbService.DBService;
import dbService.dao.DAO;
import dbService.executor.Executor;
import org.junit.Assert;
import org.junit.Test;

public class DBServiceTests {
    @Test
    public void createTableTest() {
        DAO dao = new DAO(new Executor(DBService.getInstance().getConnection()));

        Assert.assertTrue(dao.createUsersTable());
    }

    @Test
    public void dropTableTest() {
        DAO dao = new DAO(new Executor(DBService.getInstance().getConnection()));

        Assert.assertTrue(dao.dropTable());
    }

    @Test
    public void insertNewUserTest() {
        DAO dao = new DAO(new Executor(DBService.getInstance().getConnection()));
        dao.dropTable();
        dao.createUsersTable();

        Assert.assertTrue(dao.insertNewUser("admin", "admin"));
    }

    @Test
    public void insertDuplicatedLogin() {
        DAO dao = new DAO(new Executor(DBService.getInstance().getConnection()));
        dao.dropTable();
        dao.createUsersTable();
        dao.insertNewUser("admin", "admin");

        Assert.assertFalse(dao.insertNewUser("admin", "admin"));
    }
}
