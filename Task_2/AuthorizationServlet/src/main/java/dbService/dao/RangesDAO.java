package dbService.dao;

import dbService.dataSets.RangesDataSet;
import dbService.executor.Executor;

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
                                SELECT r.t_name, row_from, row_to  FROM parallel_tests.ranges r\s
                                WHERE
                                \tNOT call_flag
                                \tAND row_from = (
                                \t\tSELECT min(row_from)
                                \t\tFROM parallel_tests.ranges r1
                                \t\tWHERE t_name = r.t_name
                                \t\tGROUP BY t_name)
                                LIMIT 1""",
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

    public boolean updateRangeStatus(RangesDataSet range) {
        return executor.execUpdate(
                """
                        UPDATE parallel_tests.ranges\s
                        SET call_flag = TRUE
                        WHERE t_name = ? AND row_from = ? AND row_to = ?""",
                new Object[]{
                        range.getTableName(),
                        range.getRowStart(),
                        range.getRowEnd()
                });
    }
}
