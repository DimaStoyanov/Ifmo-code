package Paradigm.hw7.parser.res;


import Paradigm.hw7.exceptions.FormatParserException;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : ParserExceptionMessage
 * Start time : 22:08
 */
public class ParserExceptionMessage extends ParserFields {

    protected void notNumberException(String strNumber) throws FormatParserException {
        if (strNumber.equals("")) {
            String message;
            char op = stringExpression.charAt(firstIndex);
            if (op != '+' && op != '-' && op != '*' && op != '/' && op != 'm') {
                message = getUnknownLexemeExceptionMessage();
                throw new FormatParserException(message);
            }
            message = getArgumentException();
            throw new FormatParserException(message);
        }
    }

    protected void checkUnexpectedEndOfLineException() throws FormatParserException {
        if (firstIndex != len) {
            String message;
            if (stringExpression.charAt(firstIndex) == ')') {
                message = "Can't find open parenthesis at index " + firstIndex +
                        "\n\t" + getOpenBracketException();
                throw new FormatParserException(message);
            } else {
                message = getUnknownLexemeExceptionMessage();
                throw new FormatParserException(message);
            }
        }
    }

    protected String getArgumentException() {
        String message;
        String nextArg = getNextArg();
        char curOp = stringExpression.charAt(firstIndex);
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

    protected String getUnknownLexemeExceptionMessage() {
        String message;
        String prevExpression = getPrevArg(true);
        String nextExpression = getNextArg();
        char op = stringExpression.charAt(firstIndex);
        if (prevExpression.equals("")) {
            message = "Unexpected start character " + op + " at index " + firstIndex
                    + "\n\t" + op + nextExpression;
        } else if (nextExpression.equals("")) {
            message = "Unexpected end character " + op + " at index " + firstIndex
                    + "\n\t" + prevExpression + op;
        } else {
            message = "Unexpected middle character " + stringExpression.charAt(firstIndex) + " at index " + firstIndex
                    + "\n\t" + getPrevArg(true) + stringExpression.charAt(firstIndex) + getNextArg();
        }
        return message;
    }


    protected String getNextArg() {
        int start = firstIndex + 1;
        if (start == len) return "";
        char c = stringExpression.charAt(start);
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
            c = stringExpression.charAt(start);
        }
        if (balance > 0) {
            while (start != len && balance != 0) {
                c = stringExpression.charAt(start++);
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
                    c = stringExpression.charAt(start);
                }
                return sb.toString();
            }
        }
    }


    protected String getPrevArg(boolean withOp) {
        int start = Math.max(0, firstIndex - 1);
        if (start == 0)
            return "";

        while (start > 0 && Character.isWhitespace(stringExpression.charAt(start))) {
            start--;
        }
        if (start == 0)
            return String.valueOf(stringExpression.charAt(0));

        start--;
        char c = stringExpression.charAt(start);
        while (start > 0 && c != ')' && c != '(' && c != '*' && c != '/' && c != '+' && c != '-') {
            c = stringExpression.charAt(--start);
        }

        int balance = 0;
        if (c == ')') {
            balance++;
            start--;
            while (start > 0 && balance != 0) {
                c = stringExpression.charAt(start--);
                if (c == ')') {
                    balance++;
                } else if (c == '(') {
                    balance--;
                }
            }
            if (start == 0)
                return substring(firstIndex, firstIndex);

            c = stringExpression.charAt(start);
            while (start > 0 && c != '*' && c != '/' && c != '+' && c != '-') {
                c = stringExpression.charAt(--start);
            }
        }
        return substring(withOp ? start : start + 1, firstIndex);

    }

    protected String getCloseOpenBracketException() {
        int start = firstIndex - 1;
        while (start > 0 && stringExpression.charAt(start) != ')') {
            start--;
        }
        return substring(start, firstIndex);
    }

    protected String getOpenBracketException() {
        int start = firstIndex - 1;
        while (start > 0 && stringExpression.charAt(start) != '(') {
            start--;
        }
        return substring(start, firstIndex + 1);
    }

    protected String getWord() {
        int start = firstIndex;
        StringBuilder sb = new StringBuilder();
        while (start < len && Character.isAlphabetic(stringExpression.charAt(start))) {
            sb.append(stringExpression.charAt(start++));
        }
        return sb.toString();
    }

    protected String substring(int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; i++) {
            sb.append(stringExpression.charAt(i));
        }
        return sb.toString();
    }
}
