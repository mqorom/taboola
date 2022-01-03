import java.util.ArrayList;
import java.util.List;

/**
 * Problems:
 *  a- ThreadPool executor should be used. As the current implementation keep creating a thread for each transform-function
 *  Assume we have million functions then millions threads wil be created which will cause performance bad behaviour
 *  b- Threads are overwriting the data of each other which leads to wrong result
 *  c- There is no guarantee that transform functions will be executed in order way for each input
 *  d- Each thread will overwrite the result for previous one & the final result will be actually the result of the final thread!
 */
public class StringsTransformer {
    private List<String> data;

    public StringsTransformer(List<String> startingData) {
        this.data = startingData;
    }

    private void forEach(StringFunction function) {
        List<String> newData = new ArrayList<>();
        for (String str : data) {
            newData.add(function.transform(str));
        }
        data = newData;
    }

    public List<String> transform(List<StringFunction> functions) throws
            InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (final StringFunction f : functions) {
            threads.add(new Thread(() -> forEach(f)));
        }
        for (Thread t : threads) {
            t.join();
        }
        return data;
    }

    public interface StringFunction {
        String transform(String str);
    }
}
