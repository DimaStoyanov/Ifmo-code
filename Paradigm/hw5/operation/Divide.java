package Paradigm.hw5.operation;

import Paradigm.hw5.expression.TripleExpression;

/**
 * Created by Blackbird on 20.03.2016.
 * Project : Divide
 * Start time : 10:05
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
