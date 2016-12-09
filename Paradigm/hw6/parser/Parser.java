package Paradigm.hw6.parser;


import Paradigm.hw6.exceptions.FormatParserException;
import Paradigm.hw6.expression.TripleExpression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws FormatParserException;
}