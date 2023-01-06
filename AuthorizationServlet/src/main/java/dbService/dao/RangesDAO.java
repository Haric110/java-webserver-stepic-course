package dbService.dao;

import dbService.dataSets.RangesDataSet;
import dbService.executor.Executor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RangesDAO {
    private final Executor executor;

    public RangesDAO(Executor executor) {
        this.executor = executor;
    }

    public RangesDataSet getTableAndRange() {
        ArrayList<RangesDataSet> rangesList =
                executor.execQuery(
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
                            while (!rs.isLast()) {
                                rs.next();
                                resultList.add(new RangesDataSet(
                                        rs.getString(1),
                                        rs.getInt(2),
                                        rs.getInt(3)));
                            }
                            return resultList;
                        });

        assert rangesList != null;
        return rangesList.get(0);
    }

    public synchronized void updateRangeStatus(@NotNull RangesDataSet range) throws Exception {
        if (!executor.execUpdate(
                "CALL parallel_tests.update_range_status(?,?,?)",
                new Object[]{
                        range.getTableName(),
                        range.getRowStartFrom(),
                        range.getRowEndTo()
                })
        ) throw new Exception("Error when updating range for table " + range.getTableName()
                + "row_start = " + range.getRowStartFrom()
                + "row_end = " + range.getRowEndTo());
    }
}