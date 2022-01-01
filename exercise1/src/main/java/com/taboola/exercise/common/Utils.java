package com.taboola.exercise.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static Matcher matchRegex(String input, String regex) {
        return Pattern.compile(regex).matcher(input);
    }
}
