package decorator;

import java.io.PrintStream;

/**
 * Created by Dima Stoyanov.
 */
public interface Sequence<E> {
    void print(String delimiter, PrintStream ps);
    int size();
}
