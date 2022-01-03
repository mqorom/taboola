package stringfunctions;

public class StringFunctionInsert implements StringFunction {
    private String input;

    public StringFunctionInsert(String input) {
        this.input = input;

    }

    @Override
    public String transform(String str) {
        return new StringBuilder(str).insert(0, input).toString();
    }
}
