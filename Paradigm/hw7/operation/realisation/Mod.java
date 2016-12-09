package Paradigm.hw7.operation.realisation;

import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;
import Paradigm.hw7.operation.BinaryOperation;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : Mod
 * Start time : 17:07
 */
public class Mod<T> extends BinaryOperation<T> {
    public Mod(TripleExpression<T> left, TripleExpression<T> right) {
        super(left, right);
    }

    protected MyNumber<T> evaluate(MyNumber<T> left, MyNumber<T> right) {
        return left.mod(right);
    }
}
