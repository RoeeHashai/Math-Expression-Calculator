package expressions.base;

import interfaces.Expression;

import java.util.HashSet;
import java.util.List;

/**
 * An abstract class that represents a unary expression that extends the BaseExpression class.
 */
public abstract class UnaryExpression extends BaseExpression {
    private Expression operand;

    /**
     * Constructs a unary expression with the given operand.
     *
     * @param operand the operand of the unary expression
     */
    public UnaryExpression(Expression operand) {
        this.operand = operand;
    }


    /**
     * Return's the variables used in the unary expression.
     *
     * @return a list of variable names used in the expression
     */
    public List<String> getVariables() {
        List<String> var = operand.getVariables();
        HashSet<String> varSet = new HashSet<>(var);
        var.clear();
        var.addAll(varSet);
        return var;
    }

    /**
     * Returns a string representation of the unary expression.
     *
     * @return the string representation of the expression
     */
    @Override
    public String toString() {
        return "(" + operand.toString() + ")";
    }

    /**
     * Returns the operand of the expression.
     *
     * @return the operand of the expression
     */
    public Expression getOperand() {
        return operand;
    }
}
