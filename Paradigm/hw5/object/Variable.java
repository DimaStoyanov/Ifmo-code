package Paradigm.hw5.object;

import Paradigm.hw5.expression.Expression;

/**
 * Created by Blackbird on 20.03.2016.
 * Project : Variable
 * Start time : 10:19
 */

public class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public int evaluate(int x, int y, int z) {
        return name.equals("x") ? x : name.equals("y") ? y : name.equals("z") ? z : 0;
    }

}
