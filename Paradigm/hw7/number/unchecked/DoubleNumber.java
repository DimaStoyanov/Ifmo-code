package Paradigm.hw7.number.unchecked;

import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : DoubleNumber
 * Start time : 0:36
 */
public class DoubleNumber extends MyNumber<Double> {

    public DoubleNumber(Double element) {
        super(element);
    }

    public DoubleNumber(Integer element) {
        super(element.doubleValue());
    }

    public MyNumber<Double> add(MyNumber<Double> element) {
        return new DoubleNumber(value + element.value);
    }

    public MyNumber<Double> subtract(MyNumber<Double> element) {
        return new DoubleNumber(value - element.value);
    }

    public MyNumber<Double> multiply(MyNumber<Double> element) {
        return new DoubleNumber(value * element.value);
    }

    public MyNumber<Double> divide(MyNumber<Double> element) {
        return new DoubleNumber(value / element.value);
    }

    public MyNumber<Double> mod(MyNumber<Double> element) {
        return new DoubleNumber(value % element.value);
    }

    public MyNumber<Double> abs() {
        return new DoubleNumber(Math.abs(value));
    }

    public MyNumber<Double> negate() {
        return new DoubleNumber(-value);
    }

    public MyNumber<Double> sqrt() {
        return new DoubleNumber(Math.sqrt(value));
    }

    public MyNumber<Double> square() {
        return multiply(new DoubleNumber(value));
    }
}
