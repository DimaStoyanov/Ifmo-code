package Paradigm.hw5.operation;


import Paradigm.hw5.expression.TripleExpression;

/**
 * Created by Blackbird on 20.03.2016.
 * Project : Multiply
 * Start time : 10:08
 */

public class Multiply extends Operation {

    public Multiply(TripleExpression left, TripleExpression right) {
        super(left, right);
    }

    protected int evaluate(int left, int right) {
        return left * right;
    }

    protected double evaluate(double left, double right) {
        return left * right;
    }

}
