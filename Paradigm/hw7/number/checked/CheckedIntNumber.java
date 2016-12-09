package Paradigm.hw7.number.checked;


import Paradigm.hw7.exceptions.ArithmeticParserException;
import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 23.04.2016.
 * Project : CheckedIntNumber
 * Start time : 21:35
 */
public class CheckedIntNumber extends MyNumber<Integer> {

    public CheckedIntNumber(Integer value) {
        super(value);
    }

    public MyNumber<Integer> add(MyNumber<Integer> element) {
        if (element.value > 0 ? value > Integer.MAX_VALUE - element.value
                : value < Integer.MIN_VALUE - element.value) {
            throw new ArithmeticParserException("Overflow exception : " + value + " + " + element);
        }
        return new CheckedIntNumber(value + element.value);
    }

    public MyNumber<Integer> subtract(MyNumber<Integer> element) {
        if (element.value > 0 ? value < Integer.MIN_VALUE + element.value
                : value > Integer.MAX_VALUE + element.value) {
            throw new ArithmeticException("Integer overflow");
        }
        return new CheckedIntNumber(value - element.value);
    }

    public MyNumber<Integer> multiply(MyNumber<Integer> element) {
        if (element.value > 0 ? value > Integer.MAX_VALUE / element.value
                || value < Integer.MIN_VALUE / element.value
                : (element.value < -1 ? value > Integer.MIN_VALUE / element.value
                || value < Integer.MAX_VALUE / element.value
                : element.value == -1
                && value == Integer.MIN_VALUE)) {
            throw new ArithmeticParserException("Overflow exception : " + value + "*" + element);
        }
        return new CheckedIntNumber(value * element.value);
    }

    public MyNumber<Integer> divide(MyNumber<Integer> element) {
        if ((value == Integer.MIN_VALUE) && (element.value == -1)) {
            throw new ArithmeticParserException("Overflow : " + value + "/" + element);
        }
        if (element.value == 0)
            throw new ArithmeticParserException(value + "/" + element);
        return new CheckedIntNumber(value / element.value);
    }

    public MyNumber<Integer> mod(MyNumber<Integer> element) {
        if (element.value == 0)
            throw new ArithmeticParserException(value + "%" + element.value);
        return new CheckedIntNumber(value % element.value);
    }

    public MyNumber<Integer> abs() {
        if (value == Integer.MIN_VALUE) {
            throw new ArithmeticParserException("Overflow exception : abs(" + value + ")");
        }
        return new CheckedIntNumber(Math.abs(value));
    }

    public MyNumber<Integer> negate() {
        if (value == Integer.MIN_VALUE)
            throw new ArithmeticParserException("Overflow exception : -(" + value + ")");
        return new CheckedIntNumber(-value);
    }


    public MyNumber<Integer> sqrt() {
        if (value < 0)
            throw new Paradigm.hw7.exceptions.ArithmeticParserException("negative radicand :  sqrt(" + value + ")");
        return new CheckedIntNumber((int) Math.sqrt(value));
    }

    public MyNumber<Integer> square() {
        return multiply(new CheckedIntNumber(value));
    }
}
