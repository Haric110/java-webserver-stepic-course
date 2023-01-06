package sources;

import dbService.DBService;
import dbService.dao.RangesDAO;
import dbService.dataSets.RangesDataSet;
import dbService.executor.Executor;
import schedulers.StatusUpdateScheduler;

public class ParallelUpdatesSource {
    public static void main(String[] args) {
        final int THREADS_COUNT = 17;
        RangesDAO dao = new RangesDAO(new Executor(DBService.getInstance().getConnection()));
        RangesDataSet range;

        for (int i = 0; i < THREADS_COUNT; i++) {
            range = dao.getTableAndRange();
            new Thread(new StatusUpdateScheduler()).start();
        }
    }
}
