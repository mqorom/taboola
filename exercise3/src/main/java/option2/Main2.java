package option2;

import stringfunctions.StringFunction;
import stringfunctions.StringFunctionCount;
import stringfunctions.StringFunctionInsert;
import stringfunctions.StringFunctionToUpper;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        List<String> data = generateData();

        StringsTransformerOption2 option2 = new StringsTransformerOption2(data);
        option2.transform(getFunctions());

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
        for (int i = 0; i < 50000; ++i) {
            data.add("item # " + i);
        }
        return data;
    }
}
