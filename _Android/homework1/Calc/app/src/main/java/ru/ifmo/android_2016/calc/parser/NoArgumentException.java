package ru.ifmo.android_2016.calc.parser;

/**
 * Created by Blackbird on 04.04.2016.
 * Project : ExpressionParser
 * Start time : 12:37
 */
public class NoArgumentException extends RuntimeException {

    // This Exception allows you to ignore the error of missing argument
    // when parsing the expression and allows to calculate the result
    // without the last operation
     public NoArgumentException(String message){
         super(message);
     }
}
