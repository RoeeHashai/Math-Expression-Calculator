package expressions.unary;

import expressions.base.UnaryExpression;
import expressions.elements.Num;
import interfaces.Expression;

import java.util.Map;

/**
 * A class that represents a negation operation.
 */
public class Neg extends UnaryExpression {
    /**
     * Constructs a negation expression with the specified operand.
     *
     * @param operand the operand expression
     */
    public Neg(Expression operand) {
        super(operand);
    }

    /**
     * Evaluates the negation expression with the given variable assignments.
     *
     * @param assignment a map of variable assignments
     * @return the negation of the operand value
     * @throws Exception if evaluation encounters an error
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double operandValue = getOperand().evaluate(assignment);
        return -operandValue;
    }

    /**
     * Evaluates the negation expression without any variable assignments.
     *
     * @return the negation of the operand value
     * @throws Exception if evaluation encounters an error
     */
    @Override
    public double evaluate() throws Exception {
        double operandValue = getOperand().evaluate();
        return -operandValue;
    }

    /**
     * Returns a string representation of the negation expression.
     *
     * @return a string representation of the negation expression
     */
    public String toString() {
        return "(-" + super.getOperand().toString() + ")";
    }

    /**
     * Assigns a new expression to the variable in the negation expression.
     *
     * @param var        the variable to assign the expression to
     * @param expression the expression to assign
     * @return a new negation expression with the updated operand
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return new Neg(super.getOperand().assign(var, expression));
    }

    /**
     * Computes the derivative of the negation expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative expression of the negation expression
     */
    @Override
    public Expression differentiate(String var) {
        return new Neg(super.getOperand().differentiate(var));
    }

    /**
     * Simplifies the unary expression by evaluating it if possible.
     *
     * @return the simplified expression
     */
    @Override
    public Expression simplify() {
        Expression simple;
        try {
            double evaluated = this.evaluate();
            return new Num(evaluated);
        } catch (Exception evalExpError) {
            simple = super.getOperand().simplify();
            if (simple instanceof Neg) {
                Neg insideNeg = (Neg) super.getOperand();
                return insideNeg.getOperand().simplify();
            } else {
                return new Neg(simple);
            }
        }
    }
}
