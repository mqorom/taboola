package option1;

import stringfunctions.StringFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class StringsTransformerOption1 {
    private List<String> data;
    private ThreadPoolExecutor threadPool;

    public StringsTransformerOption1(List<String> startingData) {
        this.data = startingData;
        initializeThreadPool();
    }

    /**
     * Run each data item into separate thread and each thread will perform the transform functions in sequence order. Executing
     * the transforms functions in sequence way is a MUST as the output of the first transform function must be the input for the 2nd as so on.
     * No need for synchronization between threads as each one is working on separate input data
     * Assume we have data input ["1", "2", "3", "4", ........., "1 000 000"] & 5 transforms functions (f1, f2, f3, f4, f5)
     * Then 1 million threads will do our job. Each thread will execute the transform function in-order way.
     * Please note that threadPool is defined with 5 threads so only 5 concurrent threads are running in parallel
     *
     * @param functions
     * @return
     * @throws InterruptedException
     */
    public List<String> transform(List<StringFunction> functions) throws InterruptedException {
        if (data == null)
            return new ArrayList<>();

        for (int i = 0; i < data.size(); ++i) {
            int iFinal = i;
            threadPool.execute(() -> forEach(functions, iFinal));
        }
        threadPool.shutdown();
        threadPool.awaitTermination(60, TimeUnit.SECONDS);
        return data;
    }

    private void forEach(List<StringFunction> functions, int index) {
        String inputData = data.get(index);

        for (StringFunction function : functions) {
            inputData = function.transform(inputData);
        }
        data.set(index, inputData);
    }

    private void initializeThreadPool() {
        threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }
}
