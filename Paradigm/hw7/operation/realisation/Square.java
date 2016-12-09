package Paradigm.hw7.operation.realisation;

import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;
import Paradigm.hw7.operation.UnaryOperation;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : Square
 * Start time : 17:10
 */
public class Square<T> extends UnaryOperation<T> {
    public Square(TripleExpression<T> object) {
        super(object);
    }


    protected MyNumber<T> evaluate(MyNumber<T> object) {
        return object.square();
    }
}
