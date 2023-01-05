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
                                SELECT r.t_name, row_from, row_to  FROM parallel_tests.ranges r\s
                                WHERE
                                \tNOT call_flag
                                \tAND row_from = (
                                \t\tSELECT min(row_from)
                                \t\tFROM parallel_tests.ranges r1
                                \t\tWHERE t_name = r.t_name AND NOT call_flag
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

    public synchronized void updateRangeStatus(@NotNull RangesDataSet range) throws Exception {
        if (!executor.execUpdate(
                """
                        UPDATE parallel_tests.ranges\s
                        SET call_flag = TRUE
                        WHERE t_name = ? AND row_from = ? AND row_to = ?""",
                new Object[]{
                        range.getTableName(),
                        range.getRowStart(),
                        range.getRowEnd()
                }))
            throw new Exception("Error when updating range for table " + range.getTableName()
                    + "row_start = " + range.getRowStart()
                    + "row_end = " + range.getRowEnd());
    }
}
