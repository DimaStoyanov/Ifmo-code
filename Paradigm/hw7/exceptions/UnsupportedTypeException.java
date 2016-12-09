package Paradigm.hw7.exceptions;

/**
 * Created by Blackbird on 27.09.2016.
 * Project : UnsupportedTypeException
 * Start time : 17:41
 */
public class UnsupportedTypeException extends Exception {
    public UnsupportedTypeException(String message) {
        super("Unknown type : " + message);

    }
}
