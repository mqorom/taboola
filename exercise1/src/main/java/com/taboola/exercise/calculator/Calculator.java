package com.taboola.exercise.calculator;

import java.util.HashMap;
import java.util.Map;

public class Calculator {

    private Map<String, Integer> variables = new HashMap<>();

    public void execute(String equation) {
        Parser parser = new Parser(equation, variables);
        new Evaluator(parser.getEquation(), variables);
    }

    public void printResult() {
        System.out.println(variables);
    }

    /**
     * @return {@link #variables}
     */
    public Map<String, Integer> getVariables() {
        return variables;
    }
}
