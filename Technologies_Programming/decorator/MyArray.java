package decorator;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Dima Stoyanov.
 */
public class MyArray implements Sequence<Integer> {
    private Integer[] array;

    public MyArray(Integer... elements) {
        array = new Integer[elements.length];
        System.arraycopy(elements, 0, array, 0, elements.length);
    }


    @Override
    public void print(String delimiter, PrintStream ps) {
        ps.format("%s", Arrays.stream(array).map(String::valueOf).collect(Collectors.joining(delimiter)));
    }

    @Override
    public int size() {
        return array.length;
    }
}
