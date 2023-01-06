package schedulers;

import dbService.dao.RangesDAO;
import utils.RandomGen;

public class StatusUpdateScheduler implements Runnable {

    @Override
    public void run() {

        try {
            boolean flag = RangesDAO.updateRangeStatus();
            while (flag) {
                Thread.sleep(300 + RandomGen.random.nextInt(3000)); // do something with table and range...
                flag = RangesDAO.updateRangeStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
