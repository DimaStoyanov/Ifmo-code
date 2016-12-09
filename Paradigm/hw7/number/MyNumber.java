package Paradigm.hw7.number;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : MyNumber
 * Start time : 0:19
 */
public abstract class MyNumber<T> {
    public T value;

    public MyNumber(T element) {
        value = element;
    }

    public abstract MyNumber<T> add(MyNumber<T> element);

    public abstract MyNumber<T> subtract(MyNumber<T> element);

    public abstract MyNumber<T> divide(MyNumber<T> element);

    public abstract MyNumber<T> multiply(MyNumber<T> element);

    public abstract MyNumber<T> mod(MyNumber<T> element);

    public abstract MyNumber<T> abs();

    public abstract MyNumber<T> negate();

    public abstract MyNumber<T> sqrt();

    public abstract MyNumber<T> square();
}
