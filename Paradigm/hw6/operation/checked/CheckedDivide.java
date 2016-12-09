package Paradigm.hw6.operation.checked;

import Paradigm.hw6.exceptions.ArithmeticParserException;
import Paradigm.hw6.expression.TripleExpression;
import Paradigm.hw6.operation.BinaryOperation;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : CheckedDivide
 * Start time : 21:48
 */
public class CheckedDivide extends BinaryOperation {
    public CheckedDivide(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected int evaluate(int left, int right) {
        if ((left == Integer.MIN_VALUE) && (right == -1)) {
            throw new ArithmeticParserException("Overflow : " + left + "/" + right);
        }
        if (right == 0)
            throw new ArithmeticParserException(left + "/" + right);
        return left / right;
    }
}
