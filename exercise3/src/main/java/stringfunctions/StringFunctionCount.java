package stringfunctions;

public class StringFunctionCount implements StringFunction {
    @Override
    public String transform(String str) {
        return new StringBuilder(str).append(" contains ").append(str.length()).append(" characters").toString();
    }
}
