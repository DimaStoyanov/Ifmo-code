package decorator;

import java.io.PrintStream;

/**
 * Created by Dima Stoyanov.
 */
public class BracketDecorator<E> extends Decorator<E> {

    Character leftBracket, rightBracket;

    BracketDecorator(Sequence<E> sequence, char leftBracket, char rightBracket) {
        super(sequence);
        this.leftBracket = leftBracket;
        this.rightBracket = rightBracket;
    }


    @Override
    public void decorate(String delimiter, PrintStream ps) {
        ps.print(leftBracket);
        print(delimiter, ps);
        ps.print(rightBracket);
    }
}
