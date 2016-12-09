package Paradigm.hw7.parser;

import Paradigm.hw7.exceptions.FormatParserException;
import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;
import Paradigm.hw7.object.Const;
import Paradigm.hw7.object.Variable;
import Paradigm.hw7.operation.realisation.*;
import Paradigm.hw7.parser.res.ConstParser;
import Paradigm.hw7.parser.res.ParserExceptionMessage;

import java.util.Objects;

/**
 * Created by Blackbird on 04.04.2016.
 * Project : expression.exception.ExpressionParser
 * Start time : 1:37
 */
public class ExpressionParser<T> extends ParserExceptionMessage implements GenericParser<T> {

    private ConstParser<T> constParser;

    public ExpressionParser(ConstParser<T> constParser) {
        this.constParser = constParser;
    }

    public TripleExpression<T> parse(String s) throws FormatParserException {
        firstIndex = 0;
        stringExpression = s;
        len = stringExpression.length();
        TripleExpression<T> result = addSubtract();
        checkUnexpectedEndOfLineException();
        return result;
    }


    private void skipSpace() {
        while (firstIndex < len && Character.isWhitespace(stringExpression.charAt(firstIndex))) {
            firstIndex++;
        }
    }


    private TripleExpression<T> addSubtract() throws FormatParserException {
        TripleExpression<T> current;
        TripleExpression<T> accumulate = mulDivMod();
        while (firstIndex < len && stringExpression.charAt(firstIndex) != ')') {
            if (!(stringExpression.charAt(firstIndex) == '+' || stringExpression.charAt(firstIndex) == '-')) break;
            char sign = stringExpression.charAt(firstIndex);
            firstIndex++;
            current = mulDivMod();
            if (sign == '+') {
                accumulate = new Add<>(accumulate, current);
            } else {
                accumulate = new Subtract<>(accumulate, current);
            }
        }
        return accumulate;
    }


    private TripleExpression<T> mulDivMod() throws FormatParserException {
        TripleExpression<T> current = operand();
        while (true) {
            skipSpace();
            if (firstIndex >= len) {
                return current;
            }
            char sign = stringExpression.charAt(firstIndex);
            if ((sign != '*' && sign != '/' && sign != 'm')) return current;

            if (sign == 'm') {
                if (!checkMod())
                    return current;
                firstIndex += MOD_LEN;
            } else {
                firstIndex++;
            }
            TripleExpression<T> right = operand();

            if (sign == '*') {
                current = new Multiply<>(current, right);
            } else if (sign == '/') {
                current = new Divide<>(current, right);
            } else {
                current = new Mod<>(current, right);
            }
        }
    }

    private TripleExpression<T> operand() throws FormatParserException {
        skipSpace();
        if (len == firstIndex) {
            String prevExpression = getPrevArg(false);
            throw new FormatParserException("Expected argument, found end of expression at index " + (firstIndex - 1) +
                    "\n\t"
                    + prevExpression + "<missing argument>");
        }
        char c = stringExpression.charAt(firstIndex);
        switch (c) {
            case 's':
                if (checkSqrt()) {
                    return sqrtAbsSquare('s');
                } else if (checkSquare()) {
                    return sqrtAbsSquare('q');
                } else {
                    throw new FormatParserException("expected sqrt or square, found unknown lexeme at index " + firstIndex
                            + "\n\t" + getWord());
                }
            case 'a':
                if (checkAbs()) {
                    return sqrtAbsSquare('a');
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
                return new Variable<>(String.valueOf(c));
            case '-':
                firstIndex++;
                TripleExpression<T> current = operand();
                return new Negate<>(current);
            default:
                skipSpace();
                StringBuilder strNumber = new StringBuilder();
                while (firstIndex < stringExpression.length() && ((Character.isDigit(stringExpression.charAt(firstIndex))) || stringExpression.charAt(firstIndex) == '.')) {
                    strNumber.append(stringExpression.charAt(firstIndex++));
                }

                notNumberException(strNumber.toString());
                MyNumber<T> val;
                try {
                    val = constParser.parse(strNumber.toString());
                    if (Objects.equals(val.value, Integer.MIN_VALUE)) {
                        intMin = true;
                    }
                } catch (NumberFormatException e) {
                    throw new FormatParserException("Illegal number at index " + firstIndex +
                            "\n\t" + strNumber.toString());
                }
                return new Const<>(val);
        }
    }

    private TripleExpression<T> bracket() throws FormatParserException {
        firstIndex++;
        TripleExpression<T> accumulate = addSubtract();
        if (stringExpression.length() == firstIndex) {
            throw new FormatParserException("Can't find close parenthesis at index " + firstIndex
                    + "\n\t" + getCloseOpenBracketException());
        }
        if (')' == stringExpression.charAt(firstIndex)) {
            firstIndex++;
            return accumulate;
        } else {
            throw new FormatParserException("Space in constant at index " + firstIndex +
                    "\n" + getPrevArg(false) + stringExpression.charAt(firstIndex));
        }
    }


    private TripleExpression<T> sqrtAbsSquare(char type) throws FormatParserException {
        // type :
        // s - sqrt
        // a - abs
        // q - square
        TripleExpression<T> accumulate;
        firstIndex += type == 's' ? SQRT_LEN : type == 'a' ? ABS_LEN : SQR_LEN;
        accumulate = operand();
        return (type == 's') ? new Sqrt<>(accumulate) : ((type == 'a') ? new Abs<>(accumulate) : new Square<>(accumulate));
    }

    private boolean checkMod() {
        return (firstIndex + MOD_LEN - 1) < len && substring(firstIndex, firstIndex + MOD_LEN).equals("mod");
    }

    private boolean checkSquare() {
        return (firstIndex + SQR_LEN - 1) < len && substring(firstIndex, firstIndex + SQR_LEN).equals("square");
    }

    private boolean checkSqrt() {
        skipSpace();
        return (firstIndex + SQRT_LEN - 1) < len && substring(firstIndex, firstIndex + SQRT_LEN).equals("sqrt");
    }

    private boolean checkAbs() {
        skipSpace();
        return (firstIndex + ABS_LEN - 1) < len && substring(firstIndex, firstIndex + ABS_LEN).equals("abs");
    }


}