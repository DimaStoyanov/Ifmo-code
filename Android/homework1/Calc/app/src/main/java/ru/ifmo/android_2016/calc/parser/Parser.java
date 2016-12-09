package ru.ifmo.android_2016.calc.parser;




/**
 * Created by Blackbird on 04.04.2016.
 * Project : ExpressionParser
 * Start time : 12:37
 */
public interface Parser {

    /*
    * Parse and evaluate expression.
    * The parser supports the operations of addition, multiplication, subtraction, division.
    * Calculations are done in double format.
    * If expression hasn't enough arguments, parser ignore that operation
     *
     * @param String expression, contains numbers and operations
     * @return Result of expression in String format
     * @throw NumberFormatException - if expression has invalid format
     * @throw ArithmeticException - division by zer
     */
    String parse(String expression);
}