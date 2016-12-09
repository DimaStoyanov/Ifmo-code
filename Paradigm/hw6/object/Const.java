package Paradigm.hw6.object;

import Paradigm.hw6.expression.TripleExpression;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : Const
 * Start time : 21:55
 */
public class Const implements TripleExpression {
    private final int iVal;
    private final double dVal;

    public Const(int value) {
        iVal = value;
        dVal = value;
    }

    public Const(double value) {
        iVal = 0;
        dVal = value;
    }

    public int evaluate(int x) {
        return iVal;
    }

    public double evaluate(double x) {
        return dVal;
    }

    public int evaluate(int x, int y, int z) {
        return iVal;
    }

}
