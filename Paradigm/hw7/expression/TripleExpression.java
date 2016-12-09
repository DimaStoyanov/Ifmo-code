package Paradigm.hw7.expression;

import Paradigm.hw7.number.MyNumber;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface TripleExpression<T> {
    MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z);
}