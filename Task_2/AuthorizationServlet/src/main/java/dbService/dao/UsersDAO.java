package dbService.dao;

import dbService.dao.Exceptions.ArraysLengthsMismathException;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public record UsersDAO(Executor executor) {

    public boolean createUsersTable() {
        return executor.execUpdate("""
                CREATE TABLE public.users (
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
        try {
            return executor.execUpdate("insert into users (login, password) values (?, ?)",
                    2,
                    new Class<?>[]  { String.class, String.class },
                    new Object[]    { login, password } );
        } catch (ArraysLengthsMismathException e) {
            e.printStackTrace();
        }
        return false;
    }

    public @Nullable UsersDataSet getUserById(long id) throws ArraysLengthsMismathException {
        ArrayList<UsersDataSet> resultsList = executor.execQuery("""
                        select u.id, u.login, u.password
                        from users u where u.id = ?""",
                1,
                new Class[]  { Long.class },
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

    public UsersDataSet getUserByLogin(String login) throws ArraysLengthsMismathException {
        ArrayList<UsersDataSet> resultsList = executor.execQuery("""
                        select u.id, u.login, u.password
                        from users u where u.login = ?""",
                1,
                new Class[]  { String.class },
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
