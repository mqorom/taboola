package option1;

import stringfunctions.StringFunction;
import stringfunctions.StringFunctionCount;
import stringfunctions.StringFunctionInsert;
import stringfunctions.StringFunctionToUpper;

import java.util.ArrayList;
import java.util.List;

public class Main1 {
    public static void main(String[] args) throws InterruptedException {
        List<String> data = generateData();

        StringsTransformerOption1 option1 = new StringsTransformerOption1(data);
        option1.transform(getFunctions());

        System.out.println(data);
    }

    private static List<StringFunction> getFunctions() {
        List<StringFunction> functions = new ArrayList<>();

        StringFunction f1 = new StringFunctionToUpper();
        StringFunction f2 = new StringFunctionCount();
        StringFunction f3 = new StringFunctionInsert("Content=");

        functions.add(f1);
        functions.add(f2);
        functions.add(f3);
        return functions;
    }

    private static List<String> generateData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5000; ++i) {
            data.add("item # " + i);
        }
        return data;
    }
}
