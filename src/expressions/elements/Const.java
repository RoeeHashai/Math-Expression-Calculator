package expressions.elements;

import interfaces.Expression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a constant value in an expression.
 */

public class Const implements Expression {
    private String symbol;
    private double value;

    /**
     * Constructs a constant expression with the given symbol and value.
     *
     * @param symbol the symbol of the constant
     * @param value  the value of the constant
     */
    public Const(String symbol, double value) {
        this.symbol = symbol;
        this.value = value;
    }

    /**
     * Evaluates the constant expression by returning its value.
     *
     * @param assignment a map of variable assignments (not used in constant evaluation)
     * @return the value of the constant
     * @throws Exception if an error occurs during evaluation
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return value;
    }

    /**
     * Evaluates the constant expression by returning its value.
     *
     * @return the value of the constant
     * @throws Exception if an error occurs during evaluation
     */
    @Override
    public double evaluate() throws Exception {
        return value;
    }

    /**
     * Return's the variables used in the Const expression. Will always return an empty list.
     *
     * @return a list of variable names used in the expression
     */
    @Override
    public List<String> getVariables() {
        return Collections.emptyList();
    }

    /**
     * Assigns a new expression to the given variable in the constant expression.
     * Since constants do not contain variables, the method returns the constant itself.
     *
     * @param var        the variable to assign the expression to
     * @param expression the expression to assign
     * @return the constant expression itself
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return this;
    }

    /**
     * Computes the derivative of the constant expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return a constant expression with a value of 0
     */
    @Override
    public Expression differentiate(String var) {
        return new Num(0);
    }

    /**
     * Returns a string representation of the constant expression.
     *
     * @return the symbol of the constant
     */
    @Override
    public String toString() {
        return symbol;
    }


    /**
     * Simplifies the constant expression by returning the constant itself.
     *
     * @return the constant expression itself
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
