package Paradigm.hw7.operation.realisation;

import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;
import Paradigm.hw7.operation.UnaryOperation;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : Negate
 * Start time : 2:44
 */
public class Negate<T> extends UnaryOperation<T> {
    public Negate(TripleExpression<T> object) {
        super(object);
    }


    protected MyNumber<T> evaluate(MyNumber<T> object) {
        return object.negate();
    }
}
