package com.taboola.exercise.model;

import java.util.StringJoiner;

public class Operator extends Item {

    private char value;

    public Operator(char value) {
        this.value = value;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    public char getValue() {
        return value;
    }

    public boolean isLefttParanthesis() {
        return value == '(';
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Operator.class.getSimpleName() + "[", "]")
                .add("value=" + value)
                .toString();
    }

    public boolean isRightParanthesis() {
        return value == ')';
    }
}
