package expressions.elements;

import interfaces.Expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class that represents a variable in an expression.
 */
public class Var implements Expression {
    private String variableName;

    /**
     * Constructs a variable expression with the given variable name.
     *
     * @param variableName the name of the variable
     */
    public Var(String variableName) {
        this.variableName = variableName;
    }

    /**
     * Evaluates the variable expression by returning the value from the assignment map.
     *
     * @param assignment a map of variable assignments
     * @return the value of the variable from the assignment map
     * @throws Exception if the variable is not found in the assignment map
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        for (String symbol : assignment.keySet()) {
            if (symbol.equals(variableName)) {
                return assignment.get(symbol);
            }
        }
        throw new Exception("Symbol not found in assignment: " + variableName);
    }


    /**
     * Evaluates the variable expression without an assignment.
     *
     * @return this method always throws an exception
     * @throws Exception indicating that evaluation without an assignment is not supported
     */
    @Override
    public double evaluate() throws Exception {
        throw new Exception("Cannot evaluate empty assigment " + variableName);

    }

    /**
     * Returns a list containing the variable name.
     *
     * @return a list containing the variable name
     */
    @Override
    public List<String> getVariables() {
        List<String> var = new ArrayList<>();
        var.add(variableName);
        return var;
    }

    /**
     * Returns a string representation of the variable expression.
     *
     * @return a string representation of the variable name
     */
    @Override
    public String toString() {
        return variableName;
    }

    /**
     * Assigns a new expression to the given variable in the variable expression.
     *
     * @param var        the variable to assign the expression to
     * @param expression the expression to assign
     * @return the new expression if the variable name matches, otherwise the variable expression itself
     */
    @Override
    public Expression assign(String var, Expression expression) {
        if (variableName.equals(var)) {
            return expression;
        }
        return this;
    }

    /**
     * Computes the derivative of the variable expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return a numerical expression with a value of 1 if the variable name matches, otherwise 0
     */
    @Override
    public Expression differentiate(String var) {
        if (variableName.equals(var)) {
            return new Num(1);
        } else {
            return new Num(0);
        }
    }

    /**
     * Simplifies the variable expression by returning the variable expression itself.
     *
     * @return the numerical expression itself
     */
    @Override
    public Expression simplify() {
        return this;
    }
}