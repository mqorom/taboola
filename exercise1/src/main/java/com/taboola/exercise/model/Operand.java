package com.taboola.exercise.model;

import java.util.StringJoiner;

public class Operand extends Item {
    private int value;

    public Operand(int value) {
        this.value = value;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Operand.class.getSimpleName() + "[", "]")
                .add("value=" + value)
                .toString();
    }
}
