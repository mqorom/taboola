package com.taboola.exercise.calculator;

import com.taboola.exercise.common.Messages;
import com.taboola.exercise.model.Equation;
import com.taboola.exercise.model.Item;
import com.taboola.exercise.model.Operand;
import com.taboola.exercise.model.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Evaluator {

    private Equation equation;
    private Map<String, Integer> variables;

    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char MULTIPLY = '*';
    private static final char DIVIDE = '/';

    public Evaluator(Equation equation, Map<String, Integer> variables) {
        this.equation = equation;
        this.variables = variables;
        execute();
    }

    private void execute() {

        if (!this.equation.isVariableEquation()) {
            List<Item> postFixResult = constructPostFix(this.equation.getItems());
            int result = executePostFix(postFixResult);
            this.variables.put(this.equation.getVariableName(), result);
        }
        executePostOperations();
    }

    private List<Item> constructPostFix(List<Item> items) {
        Stack<Operator> stack = new Stack<>();
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (!item.isOperator()) {
                result.add(item);
            } else {
                while (!stack.isEmpty() && getPreceding(((Operator) item).getValue())
                        <= getPreceding(stack.peek().getValue())) {
                    result.add(stack.pop());
                }
                stack.push((Operator) item);
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private int executePostFix(List<Item> postFixItems) {
        Stack<Operand> operand = new Stack<>();
        int result = 0;
        for (Item item : postFixItems) {
            if (!item.isOperator()) {
                operand.push((Operand) item);
            } else {
                Integer secondOperand = operand.pop().getValue();
                Integer firstOperand = operand.pop().getValue();
                switch (((Operator) item).getValue()) {
                    case PLUS:
                        result = firstOperand + secondOperand;
                        break;
                    case MINUS:
                        result = firstOperand - secondOperand;
                        break;
                    case MULTIPLY:
                        result = firstOperand * secondOperand;
                        break;
                    case DIVIDE:
                        if (secondOperand == 0) {
                            throw new ArithmeticException(Messages.DIVIDE_BY_ZERO);
                        }
                        result = firstOperand / secondOperand;
                        break;
                    default:
                        System.out.println("unsupported operation");
                }
                operand.push(new Operand(result));
            }
        }
        return operand.pop().getValue();
    }

    private void executePostOperations() {
        executePostOperation(this.equation.getPostIncrementsVariables(), 1);
        executePostOperation(this.equation.getPostDecrementsVariables(), -1);
    }

    private void executePostOperation(List<String> postVariables, int valueToAdd) {
        for (String variable : postVariables) {
            Integer value = variables.get(variable);
            this.variables.put(variable, value + valueToAdd);
        }
    }

    private int getPreceding(char character) {
        switch (character) {
            case PLUS:
            case MINUS:
                return 1;
            case MULTIPLY:
            case DIVIDE:
                return 2;
        }
        return -1;
    }
}
