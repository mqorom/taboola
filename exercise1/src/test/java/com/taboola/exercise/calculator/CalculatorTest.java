package com.taboola.exercise.calculator;

import com.taboola.exercise.common.Messages;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    @Test
    public void oneEquation_1() {
        Calculator calculator = new Calculator();
        String input = "x = 20 * 2 - 5 / 5";
        calculator.execute(input);
        assertEquals(39, calculator.getVariables().get("x"));
    }

    @Test
    public void manyEquations1() {
        Calculator calculator = new Calculator();
        List<String> equations = new ArrayList<>();
        equations.add("i = 0");
        equations.add("j = ++i");
        equations.add("x = i++ + 5");
        equations.add("y = 5 + 3 * 10");
        equations.add("i += y");

        for (String equation : equations) {
            calculator.execute(equation);
        }
        assertEquals(37, calculator.getVariables().get("i"));
        assertEquals(1, calculator.getVariables().get("j"));
        assertEquals(6, calculator.getVariables().get("x"));
        assertEquals(35, calculator.getVariables().get("y"));
    }

    @Test
    public void manyEquations2() {
        Calculator calculator = new Calculator();
        List<String> equations = new ArrayList<>();
        equations.add("i = 5 * -1");
        equations.add("j = i-- + -1");
        equations.add("k = i++ + 5");
        equations.add("m = 6 / k");
        equations.add("i -= j");

        for (String equation : equations) {
            calculator.execute(equation);
        }
        assertEquals(1, calculator.getVariables().get("i"));
        assertEquals(-6, calculator.getVariables().get("j"));
        assertEquals(-1, calculator.getVariables().get("k"));
        assertEquals(-6, calculator.getVariables().get("m"));
    }

    @Test
    public void manyEquations3() {
        Calculator calculator = new Calculator();
        List<String> equations = new ArrayList<>();
        equations.add("i = 10 / -5");
        equations.add("j = ++i * 3");
        equations.add("k = i++ + 5 + j");
        equations.add("m = 6 / ++i + i");
        equations.add("i *= j ");

        for (String equation : equations) {
            calculator.execute(equation);
        }
        assertEquals(-3, calculator.getVariables().get("i"));
        assertEquals(-3, calculator.getVariables().get("j"));
        assertEquals(1, calculator.getVariables().get("k"));
        assertEquals(7, calculator.getVariables().get("m"));
    }

    @Test
    public void manyEquations4() {
        Calculator calculator = new Calculator();
        List<String> equations = new ArrayList<>();
        equations.add("i = 20 / 4");
        equations.add("j = i * ++i");

        for (String equation : equations) {
            calculator.execute(equation);
        }
        assertEquals(6, calculator.getVariables().get("i"));
        assertEquals(30, calculator.getVariables().get("j"));
    }

    @Test
    public void manyEquations5() {
        Calculator calculator = new Calculator();
        List<String> equations = new ArrayList<>();
        equations.add("i = 20 / 4");
        equations.add("j = ++i * i");

        for (String equation : equations) {
            calculator.execute(equation);
        }
        assertEquals(6, calculator.getVariables().get("i"));
        assertEquals(36, calculator.getVariables().get("j"));
    }

    @Test
    public void manyEquations6() {
        Calculator calculator = new Calculator();
        List<String> equations = new ArrayList<>();
        equations.add("i = 20 / 4 - 2");
        equations.add("j = ++i * i");
        equations.add("i -= j");

        for (String equation : equations) {
            calculator.execute(equation);
        }
        assertEquals(-12, calculator.getVariables().get("i"));
        assertEquals(16, calculator.getVariables().get("j"));
    }

    @Test
    public void manyEquations7() {
        Calculator calculator = new Calculator();
        List<String> equations = new ArrayList<>();
        equations.add("i = 20 / 4 - 5");
        equations.add("j = 5 / i");

        calculator.execute(equations.get(0));
        assertEquals(0, calculator.getVariables().get("i"));

        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> calculator.execute(equations.get(1)));
        assertEquals(Messages.DIVIDE_BY_ZERO, exception.getMessage());
    }
}


