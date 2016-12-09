package Paradigm.hw5.operation;


import Paradigm.hw5.expression.TripleExpression;

/**
 * Created by Blackbird on 20.03.2016.
 * Project : Subtract
 * Start time : 10:17
 */

public class Subtract extends Operation {
    public Subtract(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected int evaluate(int left, int right) {
        return left - right;
    }

    protected double evaluate(double left, double right) {
        return left - right;
    }
}
