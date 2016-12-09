package Paradigm.hw6.operation.checked;

import Paradigm.hw6.exceptions.ArithmeticParserException;
import Paradigm.hw6.expression.TripleExpression;
import Paradigm.hw6.operation.BinaryOperation;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : CheckedMultiply
 * Start time : 21:45
 */
public class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected int evaluate(int left, int right) {
        if (right > 0 ? left > Integer.MAX_VALUE / right
                || left < Integer.MIN_VALUE / right
                : (right < -1 ? left > Integer.MIN_VALUE / right
                || left < Integer.MAX_VALUE / right
                : right == -1
                && left == Integer.MIN_VALUE)) {
            throw new ArithmeticParserException("Overflow exception : " + left + "*" + right);
        }
        return left * right;
    }
}
