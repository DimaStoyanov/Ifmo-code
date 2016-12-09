package Paradigm.hw7.tabulator;

import Paradigm.hw7.exceptions.UnsupportedTypeException;
import Paradigm.hw7.expression.TripleExpression;
import Paradigm.hw7.number.MyNumber;
import Paradigm.hw7.number.checked.BigIntegerNumber;
import Paradigm.hw7.number.checked.CheckedIntNumber;
import Paradigm.hw7.number.unchecked.ByteNumber;
import Paradigm.hw7.number.unchecked.DoubleNumber;
import Paradigm.hw7.number.unchecked.FloatNumber;
import Paradigm.hw7.number.unchecked.IntNumber;
import Paradigm.hw7.parser.ExpressionParser;

import java.math.BigInteger;
import java.util.function.Function;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : GenericTabulator
 * Start time : 15:03
 */

public class GenericTabulator implements Tabulator {
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        switch (mode) {
            case "i":
                return tabulate(new ExpressionParser<>(object -> new CheckedIntNumber(Integer.parseInt(object))).parse(expression),
                        x1, x2, y1, y2, z1, z2, CheckedIntNumber::new);
            case "d":
                return tabulate(new ExpressionParser<>(object -> new DoubleNumber(Double.parseDouble(object))).parse(expression),
                        x1, x2, y1, y2, z1, z2, DoubleNumber::new);
            case "bi":
                return tabulate(new ExpressionParser<>(object -> new BigIntegerNumber(new BigInteger(object))).parse(expression),
                        x1, x2, y1, y2, z1, z2, BigIntegerNumber::new);
            case "u":
                return tabulate(new ExpressionParser<>(object -> new IntNumber(Integer.parseInt(object))).parse(expression),
                        x1, x2, y1, y2, z1, z2, IntNumber::new);
            case "b":
                return tabulate(new ExpressionParser<>(object -> new ByteNumber(Byte.parseByte(object))).parse(expression),
                        x1, x2, y1, y2, z1, z2, ByteNumber::new);
            case "f":
                return tabulate(new ExpressionParser<>(object -> new FloatNumber(new Float(object))).parse(expression),
                        x1, x2, y1, y2, z1, z2, FloatNumber::new);
            default:
                throw new UnsupportedTypeException(mode);
        }
    }

    private <T> Object[][][] tabulate(TripleExpression<T> expression, int x1, int x2, int y1, int y2, int z1, int z2, Function<Integer, MyNumber<T>> toMyNumber) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        int deltaZ = z2 - z1;
        Object[][][] table = new Object[deltaX + 1][deltaY + 1][deltaZ + 1];
        for (int x = 0; x <= deltaX; x++) {
            for (int y = 0; y <= deltaY; y++) {
                for (int z = 0; z <= deltaZ; z++) {
                    try {
                        table[x][y][z] = expression.evaluate(toMyNumber.apply(x + x1), toMyNumber.apply(y + y1),
                                toMyNumber.apply(z + z1)).value;
                    } catch (ArithmeticException e) {
                        table[x][y][z] = null;
                    }
                }
            }
        }
        return table;
    }

}
