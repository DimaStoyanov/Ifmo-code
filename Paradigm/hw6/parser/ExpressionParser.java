package Paradigm.hw6.parser;


import Paradigm.hw6.exceptions.FormatParserException;
import Paradigm.hw6.expression.TripleExpression;
import Paradigm.hw6.object.Const;
import Paradigm.hw6.object.Variable;
import Paradigm.hw6.operation.checked.*;


/**
 * Created by Blackbird on 04.04.2016.
 * Project : ExpressionParser
 * Start time : 1:37
 */

public class ExpressionParser implements Parser {
    private String rest;
    private int firstIndex;
    private boolean intMin = false;
    private int len;

    public TripleExpression parse(String s) throws FormatParserException {
        firstIndex = 0;
        rest = s;
        len = rest.length();
        TripleExpression result = addSubtract();
        if (firstIndex != len) {
            String message;
            if (rest.charAt(firstIndex) == ')') {
                message = "Can't find open parenthesis at index " + firstIndex +
                        "\n\t" + getOpenBracketException();
                throw new FormatParserException(message);
            } else {
                message = getUnknownLexemeExceptionMessage();
                throw new FormatParserException(message);
            }
        }
        return result;
    }


    private void skipSpace() {
        while (firstIndex < len && Character.isWhitespace(rest.charAt(firstIndex))) {
            firstIndex++;
        }
    }


    private TripleExpression addSubtract() throws FormatParserException {
        TripleExpression current;
        TripleExpression acc = mulDiv();
        while (firstIndex < len && rest.charAt(firstIndex) != ')') {
            if (!(rest.charAt(firstIndex) == '+' || rest.charAt(firstIndex) == '-')) break;
            char sign = rest.charAt(firstIndex);
            firstIndex++;
            current = mulDiv();
            if (sign == '+') {
                acc = new CheckedAdd(acc, current);
            } else {
                acc = new CheckedSubtract(acc, current);
            }
        }
        return acc;
    }


    private TripleExpression mulDiv() throws FormatParserException {
        TripleExpression current = operand();
        while (true) {
            skipSpace();
            if (firstIndex >= len) {
                return current;
            }
            char sign = rest.charAt(firstIndex);
            if ((sign != '*' && sign != '/')) return current;
            firstIndex++;
            TripleExpression right = operand();

            if (sign == '*') {
                current = new CheckedMultiply(current, right);
            } else {
                current = new CheckedDivide(current, right);
            }
        }
    }

    private TripleExpression operand() throws FormatParserException {
        skipSpace();
        if (len == firstIndex) {
            String prevExpression = getPrevArg(false);
            throw new FormatParserException("Expected argument, found end of Paradigm.hw6.expression at index " + (firstIndex - 1) +
                    "\n\t"
                    + prevExpression + "<missing argument>");
        }
        char c = rest.charAt(firstIndex);
        switch (c) {
            case 's':
                if (checkSqrt()) {
                    return sqrtAbs(true);
                } else {
                    throw new FormatParserException("expected sqrt, found unknown lexeme at index " + firstIndex
                            + "\n\t" + getWord());
                }
            case 'a':
                if (checkAbs()) {
                    return sqrtAbs(false);
                } else {
                    throw new FormatParserException("expected abs, found " + c + "* at index " + firstIndex
                            + "\n\t" + getWord());
                }
            case ')':
                throw new FormatParserException("Syntax parser exception : unexpected close bracket at index" + firstIndex
                        + "\n\t" + getCloseOpenBracketException());
            case '(':
                return bracket();
            case 'x':
            case 'y':
            case 'z':
                firstIndex++;
                return new Variable(String.valueOf(c));
            case '-':
                firstIndex++;
                TripleExpression current = operand();
                if (intMin) {
                    intMin = false;
                    return current;
                } else
                    return new CheckedNegate(current);
            default:
                skipSpace();
                StringBuilder strNumber = new StringBuilder();
                while (firstIndex < rest.length() && (Character.isDigit(rest.charAt(firstIndex)))) {
                    strNumber.append(rest.charAt(firstIndex++));
                }
                if (strNumber.toString().equals("")) {
                    String message;
                    char op = rest.charAt(firstIndex);
                    if (op != '+' && op != '-' && op != '*' && op != '/') {
                        message = getUnknownLexemeExceptionMessage();
                        throw new FormatParserException(message);
                    }
                    message = getArgumentException();
                    throw new FormatParserException(message);
                }
                if (strNumber.toString().equals("2147483648")) {
                    intMin = true;
                    return new Const(-2147483648);
                }
                int val;
                try {
                    val = Integer.parseInt(strNumber.toString());
                } catch (NumberFormatException e) {
                    throw new FormatParserException("Illegal number at index " + firstIndex +
                            "\n\t" + strNumber.toString());
                }
                return new Const(val);
        }
    }

    private TripleExpression bracket() throws FormatParserException {
        firstIndex++;
        TripleExpression acc = addSubtract();
        if (rest.length() == firstIndex) {
            throw new FormatParserException("Can't find close parenthesis at index " + firstIndex
                    + "\n\t" + getCloseOpenBracketException());
        }
        if (rest.charAt(firstIndex) == ')') {
            firstIndex++;
            return acc;
        } else {
            throw new FormatParserException("Space in constant at index " + firstIndex +
                    "\n" + getPrevArg(false) + rest.charAt(firstIndex));
        }
    }


    private TripleExpression sqrtAbs(boolean type) throws FormatParserException {
        TripleExpression acc;
        firstIndex += type ? 4 : 3;
        acc = operand();
        if (type) {
            return new CheckedSqrt(acc);
        } else {
            return new CheckedAbs(acc);
        }
    }

    private boolean checkSqrt() {
        skipSpace();
        return firstIndex + 4 < rest.length() && getFunc(4).equals("sqrt");
    }

    private boolean checkAbs() {
        skipSpace();
        return firstIndex + 3 < rest.length() && getFunc(3).equals("abs");
    }

    private String getFunc(int c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < c; i++) {
            sb.append(rest.charAt(firstIndex + i));
        }
        return sb.toString();
    }

    private String getArgumentException() {
        String message;
        String nextArg = getNextArg();
        char curOp = rest.charAt(firstIndex);
        String prevExpression = getPrevArg(true);
        if (prevExpression.equals("")) {
            message = "Can't find first argument at index " + firstIndex +
                    "\n\t <missing argument>" + curOp + nextArg;
        } else {
            message = "Can't find middle argument at index " + firstIndex +
                    "\n\t" + prevExpression + "<missing argument>" + curOp + nextArg;
        }
        return message;
    }

    private String getUnknownLexemeExceptionMessage() {
        String message;
        String prevExpression = getPrevArg(true);
        String nextExpression = getNextArg();
        char op = rest.charAt(firstIndex);
        if (prevExpression.equals("")) {
            message = "Unexpected start character " + op + " at index " + firstIndex
                    + "\n\t" + op + nextExpression;
        } else if (nextExpression.equals("")) {
            message = "Unexpected end character " + op + " at index " + firstIndex
                    + "\n\t" + prevExpression + op;
        } else {
            message = "Unexpected middle character " + rest.charAt(firstIndex) + " at index " + firstIndex
                    + "\n\t" + getPrevArg(true) + rest.charAt(firstIndex) + getNextArg();
        }
        return message;
    }


    private String getNextArg() {
        int start = firstIndex + 1;
        if (start == len) return "";
        char c = rest.charAt(start);
        int balance = 0;
        StringBuilder sb = new StringBuilder();
        while (start < len && !Character.isDigit(c) && c != 'x' && c != 'y' && c != 'z') {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            }
            sb.append(c);
            if (++start == len) return sb.toString();
            c = rest.charAt(start);
        }
        if (balance > 0) {
            while (start != len && balance != 0) {
                c = rest.charAt(start++);
                if (c == '(') {
                    balance++;
                } else if (c == ')') {
                    balance--;
                }
                sb.append(c);
            }
            return sb.toString();
        } else {
            if (c == 'x' || c == 'y' || c == 'z') {
                sb.append(c);
                return sb.toString();
            } else {
                while (start != len && Character.isDigit(c)) {
                    sb.append(c);
                    if (++start == len) break;
                    c = rest.charAt(start);
                }
                return sb.toString();
            }
        }
    }


    private String getPrevArg(boolean withOp) {
        int start = Math.max(0, firstIndex - 1);
        if (start == 0)
            return "";

        int end = firstIndex;
        while (start > 0 && Character.isWhitespace(rest.charAt(start))) {
            start--;
        }
        if (start == 0)
            return String.valueOf(rest.charAt(0));

        start--;
        char c = rest.charAt(start);
        while (start > 0 && c != ')' && c != '(' && c != '*' && c != '/' && c != '+' && c != '-') {
            c = rest.charAt(--start);
        }

        int balance = 0;
        if (c == ')') {
            balance++;
            start--;
            while (start > 0 && balance != 0) {
                c = rest.charAt(start--);
                if (c == ')') {
                    balance++;
                } else if (c == '(') {
                    balance--;
                }
            }
            if (start == 0)
                return substring(firstIndex, end);

            c = rest.charAt(start);
            while (start > 0 && c != '*' && c != '/' && c != '+' && c != '-') {
                c = rest.charAt(--start);
            }
        }
        return substring(withOp ? start : start + 1, end);

    }

    private String getCloseOpenBracketException() {
        int start = firstIndex - 1;
        while (start > 0 && rest.charAt(start) != ')') {
            start--;
        }
        return substring(start, firstIndex);
    }

    private String getOpenBracketException() {
        int start = firstIndex - 1;
        while (start > 0 && rest.charAt(start) != '(') {
            start--;
        }
        return substring(start, firstIndex + 1);
    }

    private String getWord() {
        int start = firstIndex;
        StringBuilder sb = new StringBuilder();
        while (start < len && Character.isAlphabetic(rest.charAt(start))) {
            sb.append(rest.charAt(start++));
        }
        return sb.toString();
    }

    private String substring(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(rest.charAt(i));
        }
        return sb.toString();
    }
}