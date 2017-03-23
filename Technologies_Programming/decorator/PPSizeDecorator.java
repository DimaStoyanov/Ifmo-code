package decorator;

import java.io.PrintStream;

/**
 * Created by Dima Stoyanov.
 */
public class PPSizeDecorator<E> extends Decorator<E> {
    private final String prefix, postfix;
    private final boolean showSize;

    PPSizeDecorator(Sequence<E> sequence, String prefix, String postfix, boolean showSize) {
        super(sequence);
        this.prefix = prefix;
        this.postfix = postfix;
        this.showSize = showSize;
    }

    @Override
    public void decorate(String delimiter, PrintStream ps) {
        ps.print(prefix);
        print(delimiter, ps);
        ps.print(postfix);
        if(showSize) ps.format(" Size: %d\n", size());
    }
}
