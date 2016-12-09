package Paradigm.hw7.number.unchecked;


import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 25.04.2016.
 * Project : IntNumber
 * Start time : 1:13
 */
public class IntNumber extends MyNumber<Integer> {
    public IntNumber(Integer value) {
        super(value);
    }

    public MyNumber<Integer> add(MyNumber<Integer> element) {
        return new IntNumber(value + element.value);
    }

    public MyNumber<Integer> subtract(MyNumber<Integer> element) {
        return new IntNumber(value - element.value);
    }

    public MyNumber<Integer> multiply(MyNumber<Integer> element) {
        return new IntNumber(value * element.value);
    }

    public MyNumber<Integer> divide(MyNumber<Integer> element) {
        return new IntNumber(value / element.value);
    }

    public MyNumber<Integer> mod(MyNumber<Integer> element) {
        return new IntNumber(value % element.value);
    }

    public MyNumber<Integer> abs() {
        return new IntNumber(Math.abs(value));
    }

    public MyNumber<Integer> negate() {
        return new IntNumber(-value);
    }


    public MyNumber<Integer> sqrt() {
        return new IntNumber((int) Math.sqrt(value));
    }

    public MyNumber<Integer> square() {
        return multiply(new IntNumber(value));
    }
}
