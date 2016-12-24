package ru.ifmo.android_2016.calc.parser;


/**
 * Created by Blackbird on 04.04.2016.
 * Project : ExpressionParser
 * Start time : 1:37
 */
public class ExpressionParser implements Parser {
    private String expression;
    private int firstIndex;
    private int len;

    public String parse(String s) {
        firstIndex = 0;
        expression = s;
        len = expression.length();
        if (len == 0) return "";

        return String.valueOf(addSubtract());
    }


    private double addSubtract() {
        double current;
        double acc = mulDiv();
        while (firstIndex < len && expression.charAt(firstIndex) != ')') {
            if (!(expression.charAt(firstIndex) == '+' || expression.charAt(firstIndex) == '-'))
                break;
            char sign = expression.charAt(firstIndex);
            firstIndex++;
            try {
                current = mulDiv();

                // Not bug but a feature
                // Expression "6+" returns 6 and didn't throw exception
            } catch (NoArgumentException e) {
                return acc;
            }
            if (sign == '+') {
                acc += current;
            } else {
                acc -= current;
            }
        }
        return acc;
    }


    private double mulDiv() {
        double current = operand();
        while (true) {
            if (firstIndex >= len) {
                return current;
            }
            char sign = expression.charAt(firstIndex);
            if ((sign != '*' && sign != '/')) return current;
            firstIndex++;
            double right;
            try {
                right = operand();

                // Not bug but a feature
                // Expression "6+" returns 6 and didn't throw exception
            } catch (NoArgumentException e) {
                return current;
            }

            if (sign == '*') {
                current *= right;
            } else {
                if (right == 0) throw new ArithmeticException("Division by zero");
                current /= right;
            }
        }
    }

    private double operand() {
        if (len == firstIndex) {
            throw new NoArgumentException("invalid format");
        }

        char c = expression.charAt(firstIndex);
        switch (c) {
            case '-':
                firstIndex++;
                return -operand();
            default:
                StringBuilder strNumber = new StringBuilder();

                // Number can consists of digits ('0' .. '9'), point, exponent, and negate after exponent
                // Example: 3.0035E-23
                while (firstIndex < expression.length() &&
                        (Character.isDigit(expression.charAt(firstIndex)) || expression.charAt(firstIndex) == 'E'
                                || expression.charAt(firstIndex) == '.' || firstIndex > 0 && expression.charAt(firstIndex - 1) == 'E' &&
                                expression.charAt(firstIndex) == '-')) {
                    strNumber.append(expression.charAt(firstIndex++));
                }
                double result = Double.parseDouble(strNumber.toString());
                if(result == Double.POSITIVE_INFINITY || result == 0 && !strNumber.toString().equals("0")){
                    throw new ArithmeticException("Overflow");
                }
                return result;
        }
    }
}





