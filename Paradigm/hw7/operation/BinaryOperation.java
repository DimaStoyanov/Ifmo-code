package Paradigm.hw7.operation;

import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;


/**
 * Created by Blackbird on 28.03.2016.
 * Project : expression.MyNumber
 * Start time : 21:24
 */
public abstract class BinaryOperation<T> implements TripleExpression<T> {
    private final TripleExpression<T> left;
    private final TripleExpression<T> right;

    public BinaryOperation(TripleExpression<T> left, TripleExpression<T> right) {
        this.left = left;
        this.right = right;
    }


    protected abstract MyNumber<T> evaluate(MyNumber<T> left, MyNumber<T> right);

    public MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) {
        return evaluate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

}
