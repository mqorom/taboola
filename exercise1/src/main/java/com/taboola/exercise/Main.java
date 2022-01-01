package com.taboola.exercise;

import com.taboola.exercise.calculator.Calculator;

public class Main {
    public static void main(String[] args) {

        int x = 1;
        int y = ++x + x;
        System.out.println(y);
        Calculator calculator = new Calculator();

        String input1 = "A = -2 - 1 + 1 + 3 ";
        String input2 = "B = -2 *  -3 - +A  +  -9 ";
        String input3 = "C = -2 *  -3 - ++A  +  -B ";

        calculator.execute(input1);
        calculator.execute(input2);
        calculator.execute(input3);
        calculator.printResult();
    }

}
