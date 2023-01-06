package dbService.dao;

import dbService.DBService;
import dbService.dataSets.RangesDataSet;
import dbService.executor.Executor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class RangesDAO {
    private final Executor EXECUTOR;
    private static final Executor STAT_EXECUTOR = new Executor(DBService.getInstance().getConnection());

    public RangesDAO(Executor executor) {
        this.EXECUTOR = executor;
    }

    private static synchronized @Nullable RangesDataSet getTableAndRange() {
        ArrayList<RangesDataSet> rangesList =
                STAT_EXECUTOR.execQuery(
                        """
                                WITH t AS (
                                	SELECT
                                		min(t1.row_from) AS min_row_from,
                                		min(t1.row_to) 	 AS min_row_to
                                	FROM (
                                		SELECT
                                			min(r1.row_from) AS row_from,
                                			min(r1.row_to) 	 AS row_to
                                		FROM
                                			parallel_tests.ranges r1
                                		WHERE NOT call_flag
                                		GROUP BY
                                			t_name
                                	) t1
                                )\s
                                SELECT
                                	r.t_name,
                                	t.min_row_from,
                                	t.min_row_to
                                FROM parallel_tests.ranges r
                                INNER JOIN t ON r.row_from = t.min_row_from
                                WHERE NOT r.call_flag
                                LIMIT 1;
                                """,
                        rs -> {
                            ArrayList<RangesDataSet> resultList = new ArrayList<>();

                            while (rs.next()) {
                                resultList.add(new RangesDataSet(
                                        rs.getString(1),
                                        rs.getInt(2),
                                        rs.getInt(3)));
                            }
                            return resultList;
                        });

        if (rangesList == null || rangesList.isEmpty()) return null;
        return rangesList.get(0);
    }

    public static synchronized boolean updateRangeStatus() throws Exception {
        RangesDataSet range = getTableAndRange();
        if (range == null) return false;

        if (!STAT_EXECUTOR.execUpdate(
                """
                        UPDATE parallel_tests.ranges\s
                        \tSET call_flag = TRUE
                        \tWHERE t_name = ? AND row_from = ? AND row_to = ?""",
                new Object[]{
                        range.getTableName(),
                        range.getRowStartFrom(),
                        range.getRowEndTo()
                })
        ) throw new Exception("Error when updating range for table " + range.getTableName()
                + "row_start = " + range.getRowStartFrom()
                + "row_end = " + range.getRowEndTo());
        else return true;

    }
}
