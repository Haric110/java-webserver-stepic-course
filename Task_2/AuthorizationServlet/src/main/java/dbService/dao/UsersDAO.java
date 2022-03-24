package dbService.dao;

import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


@SuppressWarnings("ClassCanBeRecord")
public class UsersDAO {
    private final Executor executor;

    public UsersDAO(Executor executor) {
        this.executor = executor;
        this.createUsersTable();
    }


    public boolean createUsersTable() {
        return executor.execUpdate("""
                CREATE TABLE IF NOT EXISTS public.users (
                	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
                	login varchar(256) NOT NULL,
                	"password" varchar(256) NOT NULL,
                	CONSTRAINT users_login_key UNIQUE (login),
                	CONSTRAINT users_pkey PRIMARY KEY (id)
                )""");
    }

    public boolean dropTable() {
        return executor.execUpdate("drop table if exists users");
    }

    public boolean insertNewUser(String login, String password) {
        DBService.getInstance().checkConnection();
        return executor.execUpdate("insert into users (login, password) values (?, ?)",
                new Object[] { login, password } );
    }

    public @Nullable UsersDataSet getUserById(long id) {
        ArrayList<UsersDataSet> resultsList = executor.execQuery("""
                        select u.id, u.login, u.password
                        from users u where u.id = ?""",
                new Object[] { id },
                rs -> {
                    ArrayList<UsersDataSet> resultObjList = new ArrayList<>();
                    while (!rs.isLast()) {
                        rs.next();
                        resultObjList.add(new UsersDataSet(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3)));
                    }
                    return resultObjList;
                });

        if (resultsList == null) return null;
        return resultsList.get(0);
    }

    public UsersDataSet getUserByLogin(String login) {
        ArrayList<UsersDataSet> resultsList = executor.execQuery("""
                        select u.id, u.login, u.password
                        from users u where u.login = ?""",
                new Object[] { login },
                rs -> {
                    ArrayList<UsersDataSet> resultObjList = new ArrayList<>();
                    while (rs.next()) {
                            resultObjList.add(new UsersDataSet(
                                    rs.getLong(1),
                                    rs.getString(2),
                                    rs.getString(3)));
                    }
                    return resultObjList;
                });

        if (resultsList == null || resultsList.isEmpty()) return null;
        return resultsList.get(0);
    }

    public ArrayList<UsersDataSet> getUsersByLikeCondition() {
        return executor.execQuery(
                "select u.id, u.login, u.password  from users u where login like 'TEST%'",
                rs -> {
                    ArrayList<UsersDataSet> resultObjList = new ArrayList<>();
                    while (rs.next()) {
                        resultObjList.add(new UsersDataSet(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3)));
                    }
                    return resultObjList;
                }
        );
    }
}
