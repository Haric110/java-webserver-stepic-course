package schedulers;

import dbService.DBService;
import dbService.dao.RangesDAO;
import dbService.executor.Executor;

public class StatusUpdateScheduler implements Runnable {

    @Override
    public void run() {

        try {
            while (RangesDAO.updateRangeStatus()) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
