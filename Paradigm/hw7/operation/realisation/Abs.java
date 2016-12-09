package Paradigm.hw7.operation.realisation;

import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;
import Paradigm.hw7.operation.UnaryOperation;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : Abs
 * Start time : 2:40
 */
public class Abs<T> extends UnaryOperation<T> {
    public Abs(TripleExpression<T> object) {
        super(object);
    }


    protected MyNumber<T> evaluate(MyNumber<T> object) {
        return object.abs();
    }
}
