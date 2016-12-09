package Paradigm.hw7.object;

import Paradigm.hw7.expression.CommonExpression;
import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 28.03.2016.
 * Project : Const
 * Start time : 21:55
 */
public class Const<T> implements CommonExpression<T> {
    private MyNumber<T> value;

    public Const(MyNumber<T> element) {
        value = element;
    }


    public MyNumber<T> evaluate(MyNumber<T> x, MyNumber<T> y, MyNumber<T> z) {
        return value;
    }

}
