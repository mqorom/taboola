package com.taboola.exercise.calculator;

import com.taboola.exercise.common.Constants;
import com.taboola.exercise.common.Messages;
import com.taboola.exercise.common.Utils;
import com.taboola.exercise.exception.EquationSyntaxError;
import com.taboola.exercise.exception.UnknownVariableName;
import com.taboola.exercise.model.Equation;
import com.taboola.exercise.model.Item;
import com.taboola.exercise.model.Operand;
import com.taboola.exercise.model.Operator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class Parser {

    private String input;
    private Map<String, Integer> variables;
    private Equation equation;

    public Parser(String input, Map<String, Integer> variables) {
        this.input = input;
        this.variables = variables;

        if (matchVariableOperation())
            return;

        reformatEquation();

        constructItems();
    }

    private boolean reformatEquation() {
        Matcher matcher = Utils.matchRegex(input, Constants.EQUATION_LEFT_SIDE_REGEX);

        if (matcher.find()) {
            StringBuilder inputBuilder = new StringBuilder(input);
            String variableName = matcher.group(1);
            String equationSymbol = matcher.group(2);
            inputBuilder.replace(matcher.start(1), matcher.end(3), "");
            if (!equationSymbol.isEmpty()) {
                inputBuilder.insert(0, variableName + " " + equationSymbol + " ");
            }
            this.input = inputBuilder.toString();
            this.equation = new Equation(variableName, false);
            return true;
        }
        throw new EquationSyntaxError(String.format(Messages.EQUATION_SYNTAX_ERROR_LEFT_SIDE, input));
    }


    private void constructItems() {
        List<String> splitter = Arrays.asList(this.input.trim().split(Constants.MULTI_SPACE_REGEX));
        List<Item> items = new ArrayList<>();
        Item previous = null;
        for (String item : splitter) {
            if (item.isEmpty())
                continue;

            Item current = parseItem(item);
            if (previous != null && previous.isOperator() == current.isOperator()) {
                throw new EquationSyntaxError(String.format(Messages.TWO_CONSECUTIVE_OPERANDS_OR_OPERATOR, item));
            }
            if (previous == null && current.isOperator()) {
                throw new EquationSyntaxError(String.format(Messages.RIGHT_SIDE_BEGIN_WITH_OPERATOR, item));
            }
            items.add(current);
            previous = current;
        }
        if (items.isEmpty()) {
            throw new EquationSyntaxError(Messages.RIGHT_SIDE_IS_EMPTY);
        }

        this.equation.setItems(items);
    }

    private Item parseItem(String item) {

        Matcher matcher = Utils.matchRegex(item, Constants.DIGIT_REGEX);
        if (matcher.find()) {
            return new Operand(Integer.parseInt(matcher.group(0)));
        }

        matcher = Utils.matchRegex(item, Constants.VARIABLE_WITH_SIGN_INCREMENT_DECREMENT_REGEX);

        if (matcher.find()) {
            String sign = matcher.group(1);
            String preOperator = matcher.group(2);
            String variableName = matcher.group(3);
            String postOperator = matcher.group(4);

            if (preOperator != null && postOperator != null) {
                throw new EquationSyntaxError(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, item));
            }
            Integer value = this.variables.get(variableName);
            if (value == null) {
                throw new UnknownVariableName(String.format(Messages.UNKNOWN_VARIABLE, variableName));
            }

            if (preOperator != null) {
                if (preOperator.equals(Constants.PLUS_PLUS)) {
                    value += 1;
                } else {
                    value -= 1;
                }
                variables.put(variableName, value);
            } else if (postOperator != null) {
                if (postOperator.equals(Constants.PLUS_PLUS)) {
                    this.equation.addPostIncrementsVariable(variableName);
                } else {
                    this.equation.addPostDecrementsVariable(variableName);
                }
            }

            if (sign != null && sign.equals(Constants.MINUS)) {
                value *= -1;
            }
            return new Operand(value.intValue());
        }

        matcher = Utils.matchRegex(item, Constants.OPERATOR_REGEX);
        if (matcher.find()) {
            return new Operator(matcher.group(0).charAt(0));
        }
        throw new EquationSyntaxError(String.format(Messages.UN_RECOGNIZED_ITEM, item));
    }

    private boolean matchVariableOperation() {
        Matcher matcher = Utils.matchRegex(this.input, Constants.VARIABLE_INCREMENT_DECREMENT_REGEX);

        if (matcher.find()) {
            String preOperator = matcher.group(1);
            String variableName = matcher.group(2);
            String postOperator = matcher.group(3);

            if (preOperator != null && postOperator != null) {
                throw new EquationSyntaxError(String.format(Messages.DUPLICATED_POST_OR_PER_INC_DEC, this.input));
            }
            if (variables.get(variableName) == null) {
                throw new UnknownVariableName(String.format(Messages.UNKNOWN_VARIABLE, variableName));
            }

            this.equation = new Equation(variableName, true);

            if ((preOperator != null && preOperator.equals(Constants.PLUS_PLUS))
                    || (postOperator != null && postOperator.equals(Constants.PLUS_PLUS))) {
                this.equation.addPostIncrementsVariable(variableName);
            } else {
                this.equation.addPostDecrementsVariable(variableName);
            }
            return true;
        }
        return false;
    }

    /**
     * @return {@link #equation}
     */
    public Equation getEquation() {
        return equation;
    }
}
