package Paradigm.hw6.operation.checked;

import Paradigm.hw6.exceptions.ArithmeticParserException;
import Paradigm.hw6.expression.TripleExpression;
import Paradigm.hw6.operation.UnaryOperation;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : CheckedAbs
 * Start time : 23:14
 */
public class CheckedAbs extends UnaryOperation {
    public CheckedAbs(TripleExpression object) {
        super(object);
    }

    protected int evaluate(int object) {
        if (object == Integer.MIN_VALUE) {
            throw new ArithmeticParserException("Overflow exception : abs(" + object + ")");
        }
        return object < 0 ? -object : object;
    }

}
