package Paradigm.hw6.operation.unchecked;

import Paradigm.hw6.expression.TripleExpression;
import Paradigm.hw6.operation.Operation;

/**
 * Created by Дима1 on 20.03.2016.
 */
public class Divide extends Operation {
    public Divide(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected int evaluate(int left, int right) {
        return left / right;
    }

    protected double evaluate(double left, double right) {
        return left / right;
    }
}
