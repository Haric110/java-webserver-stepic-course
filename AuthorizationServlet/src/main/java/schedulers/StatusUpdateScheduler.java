package schedulers;

import dbService.DBService;
import dbService.dao.RangesDAO;
import dbService.executor.Executor;

public class StatusUpdateScheduler implements Runnable {

    @Override
    public void run() {
        RangesDAO dao = new RangesDAO(new Executor(DBService.getInstance().getConnection()));

        try {
            dao.updateRangeStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
