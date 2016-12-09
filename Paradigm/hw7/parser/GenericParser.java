package Paradigm.hw7.parser;

import Paradigm.hw7.exceptions.FormatParserException;
import Paradigm.hw7.expression.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface GenericParser<T> {
    TripleExpression<T> parse(String expression) throws FormatParserException;
}