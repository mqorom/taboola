package com.taboola.exercise.common;

public class Constants {

    public static final String EQUATION_LEFT_SIDE_REGEX = "^\\s*([a-zA-Z_]+[\\w]*)\\s+([+-/\\*]?)(=)\\s+";

    public static final String VARIABLE_INCREMENT_DECREMENT_REGEX = "^\\s*([+]{2}|[-]{2})?([a-zA-Z_]+[\\w]*)([+]{2}|[-]{2})?\\s*$";

    public static final String MULTI_SPACE_REGEX = "\\s+";

    public static final String DIGIT_REGEX = "^([+-]?[\\d]+)$";

    public static final String VARIABLE_WITH_SIGN_INCREMENT_DECREMENT_REGEX = "^([+-])?([+]{2}|[-]{2})?([a-zA-Z_]+[\\w]*)([+]{2}|[-]{2})?$";

    public static final String PLUS_PLUS = "++";

    public static final String MINUS_MINUS = "--";

    public static final String MINUS = "-";

    public static final String OPERATOR_REGEX = "^([+-/*]?)$" ;
}
