package decorator;

import java.io.PrintStream;

/**
 * Created by Dima Stoyanov.
 */
public abstract class Decorator<E> implements Sequence<E> {
    private Sequence<E> sequence;

    Decorator(Sequence<E> sequence){
        this.sequence = sequence;
    }


    @Override
    public int size() {
        return sequence.size();
    }

    @Override
    public void print(String delimiter, PrintStream ps) {
        sequence.print(delimiter, ps);
    }

    public abstract void decorate(String delimiter, PrintStream ps);
}
