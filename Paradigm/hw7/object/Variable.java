package Paradigm.hw7.object;

import Paradigm.hw7.expression.CommonExpression;
import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : Variable
 * Start time : 21:53
 */
public class Variable<T> implements CommonExpression<T> {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new RuntimeException("unexpected name of variable");
        }

    }

}
