package option2;

import stringfunctions.StringFunction;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 */
public class StringsTransformerOption2 {
    private List<String> data;
    private ThreadPoolExecutor threadPool;

    public StringsTransformerOption2(List<String> startingData) {
        this.data = startingData;
        initializeThreadPool();
    }

    /**
     * Run each data item & function into separate thread. Synchronization between threads is required here.
     * Let say we have data ["1", "2", "3", ..... , "1 000 000"] & functions ["f1", "f2", "f3", "f4", "f5"] then 5 millions threads will be executed
     * The threads that belong to f3 will wait for f2 to complete and f2 threads will wait for f1 and so on
     *
     * The final result can be fetched only after executing all threads but in the first approach the result of each item is final as long as it
     * corresponding thread is done
     * @param functions
     * @return
     * @throws InterruptedException
     */
    public List<String> transform(List<StringFunction> functions) throws InterruptedException {
        for (final StringFunction stringFunction : functions) {
            final CountDownLatch latch = new CountDownLatch(data.size());
            for (int i = 0; i < data.size(); ++i) {
                int finalI = i;
                threadPool.execute(() -> {
                    String result = stringFunction.transform(data.get(finalI));
                    data.set(finalI, result);
                    latch.countDown();
                });
            }
            latch.await();
        }
        return data;
    }

    private void initializeThreadPool() {
        threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }
}
