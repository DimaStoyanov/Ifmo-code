package Paradigm.hw6.operation.checked;

import Paradigm.hw6.exceptions.ArithmeticParserException;
import Paradigm.hw6.expression.TripleExpression;
import Paradigm.hw6.operation.UnaryOperation;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : CheckedSqrt
 * Start time : 23:17
 */
public class CheckedSqrt extends UnaryOperation {
    public CheckedSqrt(TripleExpression object) {
        super(object);
    }

    //  also the way to find sqrt (if numbers are int) is using binary search (from 1 to x)
    protected int evaluate(int x) {
        if (x < 0)
            throw new ArithmeticParserException("negative radicand :  sqrt(" + x + ")");
        if (x == 0)
            return 0;
        int result = 1;
        boolean decreased = false;
        int nx;
        for (; ; ) {
            nx = (result + x / result) >> 1;
            if (result == nx || nx > result && decreased) {
                break;
            }
            decreased = nx < result;
            result = nx;
        }
        return result;
    }
}
