package Paradigm.hw7.operation.realisation;

import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;
import Paradigm.hw7.operation.UnaryOperation;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : Sqrt
 * Start time : 2:45
 */
public class Sqrt<T> extends UnaryOperation<T> {
    public Sqrt(TripleExpression<T> object) {
        super(object);
    }


    protected MyNumber<T> evaluate(MyNumber<T> object) {
        return object.sqrt();
    }
}
