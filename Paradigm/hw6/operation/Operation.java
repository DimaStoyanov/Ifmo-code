package Paradigm.hw6.operation;


import Paradigm.hw6.expression.TripleExpression;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : Operation
 * Start time : 19:06
 */

public abstract class Operation implements TripleExpression {
    private final TripleExpression left;
    private final TripleExpression right;

    public Operation(TripleExpression left, TripleExpression right) {
        this.left = left;
        this.right = right;
    }

    protected abstract int evaluate(int left, int right);


    public int evaluate(int x, int y, int z) {
        return evaluate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

}
