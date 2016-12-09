package Paradigm.hw5.object;

import Paradigm.hw5.expression.Expression;

/**
 * Created by Blackbird on 20.03.2016.
 * Project : Const
 * Start time : 11:22
 */

public class Const implements Expression {
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
