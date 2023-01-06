package sources;

import schedulers.StatusUpdateScheduler;

public class ParallelUpdatesSource {
    public static void main(String[] args) {
        final int THREADS_COUNT = 17;

        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(new StatusUpdateScheduler()).start();
        }
    }
}
