package decorator;

import java.io.PrintStream;

/**
 * Created by Dima Stoyanov.
 */
public class Test {
    public static void main(String[] args) {
        Sequence<Integer> array = new MyArray(1,3,5,6,1);
        array.print(",", new PrintStream(System.out));
        System.out.println();gigig
        Decorator<Integer> decorator = new PPSizeDecorator<>(array, "Elements: ", ".", true);
        decorator.decorate(", ", new PrintStream(System.out));
    }
}
