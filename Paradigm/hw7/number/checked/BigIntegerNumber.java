package Paradigm.hw7.number.checked;

import Paradigm.hw6.exceptions.ArithmeticParserException;
import Paradigm.hw7.number.MyNumber;

import java.math.BigInteger;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : BigIntegerNumber
 * Start time : 0:39
 */
public class BigIntegerNumber extends MyNumber<BigInteger> {
    public BigIntegerNumber(BigInteger element) {
        super(element);
    }

    public BigIntegerNumber(Integer element) {
        super(BigInteger.valueOf(element));
    }

    public MyNumber<BigInteger> add(MyNumber<BigInteger> element) {
        return new BigIntegerNumber(value.add(element.value));
    }

    public MyNumber<BigInteger> subtract(MyNumber<BigInteger> element) {
        return new BigIntegerNumber(value.subtract(element.value));
    }

    public MyNumber<BigInteger> multiply(MyNumber<BigInteger> element) {
        return new BigIntegerNumber(value.multiply(element.value));
    }

    public MyNumber<BigInteger> divide(MyNumber<BigInteger> element) {
        if (element.value.equals(BigInteger.ZERO)) {
            throw new ArithmeticParserException(value + "/" + element.value);
        }
        return new BigIntegerNumber(value.divide(element.value));
    }

    public MyNumber<BigInteger> mod(MyNumber<BigInteger> element) {
        return new BigIntegerNumber(value.mod(element.value));
//        return new BigIntegerNumber(value.subtract(value.divide(element.value).multiply(element.value)));
    }

    public MyNumber<BigInteger> abs() {
        return new BigIntegerNumber(value.abs());
    }

    public MyNumber<BigInteger> negate() {
        return new BigIntegerNumber(value.negate());
    }

    public MyNumber<BigInteger> sqrt() {
        BigInteger left = BigInteger.ZERO;
        BigInteger right = value;
        while (left.compareTo(right) < 0) {
            BigInteger middle = (left.add(right)).shiftRight(1);
            if (middle.multiply(middle).compareTo(value) >= 0) {
                right = middle;
            } else {
                left = middle.add(BigInteger.ONE);
            }
        }
        return new BigIntegerNumber(right);
    }

    public MyNumber<BigInteger> square() {
        return multiply(new BigIntegerNumber(value));
    }
}
