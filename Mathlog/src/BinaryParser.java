package Mathlog.src;

import com.sun.istack.internal.NotNull;
import jdk.nashorn.internal.runtime.ParserException;

/**
 * Created by Blackbird on 05.10.2016.
 * Project : BinaryParser
 * Start time : 22:44
 */

class BinaryParser {
    private final int LOW_PRIORITY = 0;
    private final int HIGH_PRIORITY = 3;
    private int curIndex;
    private char[] expression;

    private void init(@NotNull String expr) {
        expression = expr.toCharArray();
        curIndex = 0;
    }

    @NotNull
    ExpressionNode parse(@NotNull String expr) {
        init(expr);
        return parseByPriority(LOW_PRIORITY);
    }

    @NotNull
    private ExpressionNode parseByPriority(int priority) {
        skipSpaces();
        if (priority == HIGH_PRIORITY) {
            return parseUnary();
        }
        ExpressionNode acc = parseByPriority(priority + 1);
        skipSpaces();
        while (!end() && equalsOpPriority(priority)) {
            ExpressionNode.Operation op = getOperation();
            skipSpaces();
            // Right association
            if (priority == LOW_PRIORITY) {
                curIndex += 2;
                return new ExpressionNode(acc, parseByPriority(LOW_PRIORITY), op);
                // Left association
            } else {
                curIndex++;
                acc = new ExpressionNode(acc, parseByPriority(priority + 1), op);
            }

        }
        return acc;
    }

    @NotNull
    private ExpressionNode parseUnary() {
        if (end()) {
            throw new ParserException("Not enough arguments");
        }
        skipSpaces();
        char curChar = expression[curIndex];
        if (curChar == '(') {
            curIndex++;
            ExpressionNode acc = parseByPriority(LOW_PRIORITY);
            skipSpaces();
            if (expression[curIndex] != ')') {
                throw new ParserException("Missed close bracket '" + expression[curIndex] + "' at index " + curIndex
                        + "\n" + (new String(expression)));
            }
            curIndex++;
            return acc;
        } else if (curChar == '!') {
            curIndex++;
            return new ExpressionNode(parseUnary(), ExpressionNode.Operation.NOT);
        } else if (Character.isLetter(curChar)) {
            return parseVariable();
        } else {
            throw new ParserException("Unexpected format of argument " + curChar +
                    "\n" + (new String(expression)));
        }
    }

    @NotNull
    private ExpressionNode parseVariable() {
        StringBuilder var = new StringBuilder();
        char curChar = expression[curIndex];
        while ((Character.isDigit(curChar) || Character.isLetter(curChar))) {
            var.append(curChar);
            if (nextEnd()) break;
            curChar = expression[++curIndex];
        }
        return new ExpressionNode(var.toString());
    }


    private boolean equalsOpPriority(int priority) {
        ExpressionNode.Operation op = getOperation();
        return priority == 0 ? op == ExpressionNode.Operation.IMP :
                priority == 1 ? op == ExpressionNode.Operation.OR :
                        priority == 2 && op == ExpressionNode.Operation.AND;
    }

    @NotNull
    private ExpressionNode.Operation getOperation() {
        char c = expression[curIndex];
        return c == '-' && !nextEnd() && expression[curIndex + 1] == '>' ?
                ExpressionNode.Operation.IMP :
                c == '|' ? ExpressionNode.Operation.OR :
                        c == '&' ? ExpressionNode.Operation.AND :
                                c == '!' ? ExpressionNode.Operation.NOT :
                                        ExpressionNode.Operation.NOP;
    }


    private void skipSpaces() {
        while (!end() && Character.isWhitespace(expression[curIndex])) {
            curIndex++;
        }
    }

    private boolean end() {
        return curIndex >= expression.length;
    }

    private boolean nextEnd() {
        return (curIndex + 1) >= expression.length;
    }


}
