package decorator;

import java.io.PrintStream;

/**
 * Created by Dima Stoyanov.
 */
public class PPDecorator<E> extends Decorator<E> {

    private final String prefix, postfix;

    PPDecorator(Sequence<E> sequence, String prefix, String postfix) {
        super(sequence);
        this.prefix = prefix;
        this.postfix = postfix;
    }

    @Override
    public void decorate(String delimiter, PrintStream ps) {
        ps.print(prefix);
        print(delimiter, ps);
        ps.print(postfix);
    }
}
