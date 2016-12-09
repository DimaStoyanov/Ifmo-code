package Paradigm.hw6.operation;


import Paradigm.hw6.expression.TripleExpression;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : UnaryOperation
 * Start time : 23:09
 */
public abstract class UnaryOperation implements TripleExpression {
    private final TripleExpression object;

    public UnaryOperation(TripleExpression object) {
        this.object = object;
    }

    abstract protected int evaluate(int object);

    public int evaluate(int x, int y, int z) {
        return evaluate(object.evaluate(x, y, z));
    }
}
