package expressions.elements;

import interfaces.Expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a numerical value in an expression.
 */
public class Num implements Expression {
    private double num;

    /**
     * Constructs a numerical expression with the given value.
     *
     * @param num the numerical value
     */
    public Num(double num) {
        this.num = num;
    }

    /**
     * Evaluates the numerical expression by returning its value.
     *
     * @param assignment a map of variable assignments (not used in numerical evaluation)
     * @return the value of the numerical expression
     * @throws Exception if an error occurs during evaluation
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return num;
    }

    /**
     * Evaluates the numerical expression.
     *
     * @return the value of the numerical expression
     * @throws Exception if an error occurs during evaluation
     */
    @Override
    public double evaluate() throws Exception {
        return num;
    }

    /**
     * Returns a list of variables of the expression (an empty list).
     *
     * @return an empty list
     */
    @Override
    public List<String> getVariables() {
        return Collections.emptyList();
    }

    /**
     * Returns a string representation of the numerical expression.
     *
     * @return a string representation of the numerical value
     */
    @Override
    public String toString() {
        return String.valueOf(num);
    }

    /**
     * Assigns a new expression to the given variable in the numerical expression.
     *
     * @param var        the variable to assign the expression to
     * @param expression the expression to assign
     * @return the numerical expression itself
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    /**
     * Computes the derivative of the numerical expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return a numerical expression with a value of 0
     */
    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    /**
     * Simplifies the numerical expression by returning the numerical expression itself.
     *
     * @return the numerical expression itself
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
