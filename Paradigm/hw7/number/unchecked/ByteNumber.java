package Paradigm.hw7.number.unchecked;

import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 25.04.2016.
 * Project : ByteNumber
 * Start time : 1:18
 */
public class ByteNumber extends MyNumber<Byte> {

    public ByteNumber(Byte value) {
        super(value);
    }

    public ByteNumber(Integer value) {
        super(value.byteValue());
    }

    public MyNumber<Byte> add(MyNumber<Byte> element) {
        return new ByteNumber((byte) (value + element.value));
    }

    public MyNumber<Byte> subtract(MyNumber<Byte> element) {
        return new ByteNumber((byte) (value - element.value));
    }

    public MyNumber<Byte> multiply(MyNumber<Byte> element) {
        return new ByteNumber((byte) (value * element.value));
    }

    public MyNumber<Byte> divide(MyNumber<Byte> element) {
        return new ByteNumber((byte) (value / element.value));
    }

    public MyNumber<Byte> mod(MyNumber<Byte> element) {
        return new ByteNumber((byte) (value % element.value));
    }

    public MyNumber<Byte> abs() {
        return new ByteNumber((byte) Math.abs(value));
    }

    public MyNumber<Byte> negate() {
        return new ByteNumber((byte) -value);
    }


    public MyNumber<Byte> sqrt() {
        return new ByteNumber((byte) Math.sqrt(value));
    }

    public MyNumber<Byte> square() {
        return multiply(new ByteNumber(value));
    }
}
