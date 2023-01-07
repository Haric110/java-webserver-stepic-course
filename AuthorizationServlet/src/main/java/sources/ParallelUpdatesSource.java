package sources;

import org.jetbrains.annotations.NotNull;
import schedulers.StatusUpdateScheduler;

public class ParallelUpdatesSource {
    public static void main(String @NotNull [] args) {
        final int THREADS_COUNT = Integer.parseInt(args[0]);

        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(new StatusUpdateScheduler()).start();
        }
    }
}
