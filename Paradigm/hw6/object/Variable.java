package Paradigm.hw6.object;

import Paradigm.hw6.expression.TripleExpression;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : Variable
 * Start time : 21:53
 */
public class Variable implements TripleExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public int evaluate(int x, int y, int z) {
        return name.equals("x") ? x : name.equals("y") ? y : name.equals("z") ? z : 0;

    }

}
