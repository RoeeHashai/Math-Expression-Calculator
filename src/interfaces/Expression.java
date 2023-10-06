package interfaces;

import java.util.List;
import java.util.Map;

/**
 * An interface that defines the behavior of an arithmetic expression.
 */
public interface Expression {
    /**
     * Evaluates the expression using variable assignments and returns the result.
     *
     * @param assignment variable assignments
     * @return the result of evaluating the expression
     * @throws Exception if evaluation encounters an error
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Evaluates the expression using an empty assignment.
     *
     * @return the result of evaluating the expression
     * @throws Exception if evaluation encounters an error
     */
    double evaluate() throws Exception;

    /**
     * Returns a list of variables in the expression.
     *
     * @return a list of variables
     */
    List<String> getVariables();

    /**
     * Returns a string representation of the expression.
     *
     * @return a string representation of the expression
     */
    String toString();

    /**
     * Replaces occurrences of a variable with the provided expression.
     *
     * @param var        the variable to replace
     * @param expression the expression to substitute
     * @return a new expression with the variable replaced
     */
    Expression assign(String var, Expression expression);

    /**
     * Computes the derivative of the expression with respect to a variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative expression
     */
    Expression differentiate(String var);

    /**
     * Returns a simplified version of the expression.
     *
     * @return a simplified expression
     */
    Expression simplify();
}