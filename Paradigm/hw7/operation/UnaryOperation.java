package Paradigm.hw7.operation;

import Paradigm.hw7.expression.CommonExpression;
import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : UnaryOperation
 * Start time : 23:09
 */
public abstract class UnaryOperation<T> implements CommonExpression<T> {
    private final TripleExpression<T> object;

    public UnaryOperation(TripleExpression<T> object) {
        this.object = object;
    }

    abstract protected MyNumber<T> evaluate(MyNumber<T> object);

    public MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) {
        return evaluate(object.evaluate(x, y, z));
    }
}
