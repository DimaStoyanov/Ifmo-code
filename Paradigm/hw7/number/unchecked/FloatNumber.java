package Paradigm.hw7.number.unchecked;

import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 25.04.2016.
 * Project : FloatNumber
 * Start time : 1:15
 */
public class FloatNumber extends MyNumber<Float> {
    public FloatNumber(Float value) {
        super(value);
    }

    public FloatNumber(Integer value) {
        super(value.floatValue());
    }

    public MyNumber<Float> add(MyNumber<Float> element) {
        return new FloatNumber(value + element.value);
    }

    public MyNumber<Float> subtract(MyNumber<Float> element) {
        return new FloatNumber(value - element.value);
    }

    public MyNumber<Float> multiply(MyNumber<Float> element) {
        return new FloatNumber(value * element.value);
    }

    public MyNumber<Float> divide(MyNumber<Float> element) {
        return new FloatNumber(value / element.value);
    }

    public MyNumber<Float> mod(MyNumber<Float> element) {
        return new FloatNumber(value % element.value);
    }

    public MyNumber<Float> abs() {
        return new FloatNumber(Math.abs(value));
    }

    public MyNumber<Float> negate() {
        return new FloatNumber(-value);
    }


    public MyNumber<Float> sqrt() {
        return new FloatNumber(((float) Math.sqrt(value)));
    }

    public MyNumber<Float> square() {
        return multiply(new FloatNumber(value));
    }
}
