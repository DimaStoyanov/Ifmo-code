package Paradigm.hw7.parser.res;

import Paradigm.hw7.number.MyNumber;

/**
 * Created by Blackbird on 24.04.2016.
 * Project : ConstParser
 * Start time : 5:12
 */
public interface ConstParser<T> {
    MyNumber<T> parse(String object);
}
