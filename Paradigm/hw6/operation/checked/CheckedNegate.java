package Paradigm.hw6.operation.checked;

import Paradigm.hw6.exceptions.ArithmeticParserException;
import Paradigm.hw6.expression.TripleExpression;
import Paradigm.hw6.operation.UnaryOperation;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : CheckedNegate
 * Start time : 23:58
 */
public class CheckedNegate extends UnaryOperation {
    public CheckedNegate(TripleExpression object) {
        super(object);
    }

    protected int evaluate(int object) {
        if (object == Integer.MIN_VALUE)
            throw new ArithmeticParserException("Overflow exception : -(" + object + ")");
        return -object;
    }
}
