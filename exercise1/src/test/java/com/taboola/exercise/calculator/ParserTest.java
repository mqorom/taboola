package com.taboola.exercise.calculator;

import com.taboola.exercise.common.Messages;
import com.taboola.exercise.exception.EquationSyntaxError;
import com.taboola.exercise.exception.UnknownVariableName;
import com.taboola.exercise.model.Equation;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void assignmentEquation1_parsedCorrectly() {
        String input = "x = 20 * 2 - 5 / 5";
        Parser parser = new Parser(input, new HashMap<>());
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 7);
    }

    @Test
    public void assignmentPlusEquation1_parsedCorrectly() {
        String input = "x += 20 * 2 - 5 / 5";
        Map<String, Integer> variables = new HashMap();
        variables.put("x", -2);

        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 9);
    }

    @Test
    public void assignmentPlusEquation2_parsedCorrectly() {
        String input = "_x += -20 * 2 - +5 / -5";
        Map<String, Integer> variables = new HashMap();
        variables.put("_x", -2);

        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 9);
    }

    @Test
    public void assignmentMinusEquation1_parsedCorrectly() {
        String input = "x -= -20 / 2 - ++y / -5";
        Map<String, Integer> variables = new HashMap();
        variables.put("x", -2);
        variables.put("y", -2);

        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 9);
    }

    @Test
    public void assignmentMinusEquation2_parsedCorrectly() {
        String input = "x -= -20 / 2 - y-- / -5";
        Map<String, Integer> variables = new HashMap();
        variables.put("x", -2);
        variables.put("y", -2);

        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 9);
    }

    @Test
    public void assignmentMultiplyEquation1_parsedCorrectly() {
        String input = "x *= -20 * 2 - y-- / ++x";
        Map<String, Integer> variables = new HashMap();
        variables.put("x", -2);
        variables.put("y", -2);

        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 9);
    }

    @Test
    public void assignmentMultiplyEquation2_parsedCorrectly() {
        String input = "x *= -20 * x++ - --y / -5";
        Map<String, Integer> variables = new HashMap();
        variables.put("x", -2);
        variables.put("y", -2);

        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 9);
    }

    @Test
    public void assignmentDivideEquation1_parsedCorrectly() {
        String input = "x /= -20 / x++ - --y / -5";
        Map<String, Integer> variables = new HashMap();
        variables.put("x", -2);
        variables.put("y", -2);

        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 9);
    }

    @Test
    public void assignmentDivideEquation2_parsedCorrectly() {
        String input = "x /= -20 * x++ - --y / -5";
        Map<String, Integer> variables = new HashMap();
        variables.put("x", -2);
        variables.put("y", -2);

        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertFalse(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 9);
    }

    @Test
    public void variablePreIncrement() {
        String input = "++x";
        Map<String, Integer> variables = new HashMap();
        variables.put("x", -2);
        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertTrue(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 0);
    }

    @Test
    public void variablePreDecrement() {
        String input = "--y";
        Map<String, Integer> variables = new HashMap();
        variables.put("y", -2);
        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertTrue(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 0);
    }

    @Test
    public void variablePostDecrement() {
        String input = "y--";
        Map<String, Integer> variables = new HashMap();
        variables.put("y", -2);
        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertTrue(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 0);
    }

    @Test
    public void variablePostIncrement() {
        String input = "y++";
        Map<String, Integer> variables = new HashMap();
        variables.put("y", -2);
        Parser parser = new Parser(input, variables);
        Equation equation = parser.getEquation();

        assertTrue(equation.isVariableEquation());
        assertEquals(equation.getItems().size(), 0);
    }

    @Test
    public void leftSide_error1() {
        String input = "y++ = 1 + 1";
        Map<String, Integer> variables = new HashMap();
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.EQUATION_SYNTAX_ERROR_LEFT_SIDE, input), exception.getMessage());
    }

    @Test
    public void leftSide_error2() {
        String input = "9 += 1 + 1";
        Map<String, Integer> variables = new HashMap();
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.EQUATION_SYNTAX_ERROR_LEFT_SIDE, input), exception.getMessage());
    }

    @Test
    public void twoConsecutiveOperands_error() {
        String input = "A = 10 1";
        Map<String, Integer> variables = new HashMap();
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.TWO_CONSECUTIVE_OPERANDS_OR_OPERATOR, "1"), exception.getMessage());
    }

    @Test
    public void twoConsecutiveOperators_error() {
        String input = "A = 5 + + 1";
        Map<String, Integer> variables = new HashMap();
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.TWO_CONSECUTIVE_OPERANDS_OR_OPERATOR, "+"), exception.getMessage());
    }

    @Test
    public void rightSideEmpty_error() {
        String input = "A =  ";
        Map<String, Integer> variables = new HashMap();
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.RIGHT_SIDE_IS_EMPTY), exception.getMessage());
    }

    @Test
    public void rightSideBeginWithOperator_error() {
        String input = "A = + 9 ";
        Map<String, Integer> variables = new HashMap();
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.RIGHT_SIDE_BEGIN_WITH_OPERATOR, "+"), exception.getMessage());
    }

    @Test
    public void duplicatesPostIncrements_error1() {
        String input = "A = 1 + ++B++ ";
        Map<String, Integer> variables = new HashMap();
        variables.put("B", 1);
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, "++B++"), exception.getMessage());
    }

    @Test
    public void duplicatesPostIncrementsDecrements_error2() {
        String input = "A = 1 + ++B-- ";
        Map<String, Integer> variables = new HashMap();
        variables.put("B", 1);
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, "++B--"), exception.getMessage());
    }

    @Test
    public void duplicatesPostIncrementsDecrements_error3() {
        String input = "A = 1 + --B++ ";
        Map<String, Integer> variables = new HashMap();
        variables.put("B", 1);
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, "--B++"), exception.getMessage());
    }

    @Test
    public void duplicatesPostDecrements_error4() {
        String input = "A = 1 + --B-- ";
        Map<String, Integer> variables = new HashMap();
        variables.put("B", 1);
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, "--B--"), exception.getMessage());
    }

    @Test
    public void duplicatesPostDecrements_error5() {
        String input = "--B--";
        Map<String, Integer> variables = new HashMap();
        variables.put("B", 1);
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, input), exception.getMessage());
    }

    @Test
    public void duplicatesPostIncrementsDecrements_error6() {
        String input = "--B++";
        Map<String, Integer> variables = new HashMap();
        variables.put("B", 1);
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, input), exception.getMessage());
    }

    @Test
    public void duplicatesPostIncrementsDecrements_error7() {
        String input = "++B--";
        Map<String, Integer> variables = new HashMap();
        variables.put("B", 1);
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, input), exception.getMessage());
    }

    @Test
    public void duplicatesPostIncrements_error8() {
        String input = "++B++";
        Map<String, Integer> variables = new HashMap();
        variables.put("B", 1);
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, input), exception.getMessage());
    }

    @Test
    public void unknownVariable_error1() {
        String input = "A = 1 + B-- ";
        Map<String, Integer> variables = new HashMap();
        UnknownVariableName exception = assertThrows(UnknownVariableName.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.UNKNOWN_VARIABLE, 'B'), exception.getMessage());
    }

    @Test
    public void unknownItem_error1() {
        String input = "A = 1 + +++1 ";
        Map<String, Integer> variables = new HashMap();
        EquationSyntaxError exception = assertThrows(EquationSyntaxError.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.UN_RECOGNIZED_ITEM, "+++1"), exception.getMessage());
    }

    @Test
    public void unknownVariable_error() {
        String input = "++A";
        Map<String, Integer> variables = new HashMap();
        UnknownVariableName exception = assertThrows(UnknownVariableName.class, () -> new Parser(input, variables));
        assertEquals(String.format(Messages.UNKNOWN_VARIABLE, 'A'), exception.getMessage());
    }
}
