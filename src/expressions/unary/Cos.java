package expressions.unary;

import expressions.base.UnaryExpression;
import expressions.binary.Mult;
import expressions.elements.Num;
import interfaces.Expression;

import java.util.Map;

/**
 * A class that represents a Cosine operation.
 */
public class Cos extends UnaryExpression {
    /**
     * Constructs a cosine expression with the specified operand.
     *
     * @param operand the operand expression
     */
    public Cos(Expression operand) {
        super(operand);
    }

    /**
     * Evaluates the cosine expression with the given variable assignments.
     *
     * @param assignment a map of variable assignments
     * @return the cosine of the operand value
     * @throws Exception if evaluation encounters an error
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double operandValue = getOperand().evaluate(assignment);
        return Math.cos(Math.toRadians(operandValue));
    }

    /**
     * Evaluates the cosine expression without any variable assignments.
     *
     * @return the cosine of the operand value
     * @throws Exception if evaluation encounters an error
     */
    @Override
    public double evaluate() throws Exception {
        double operandValue = getOperand().evaluate();
        return Math.cos(Math.toRadians(operandValue));
    }

    /**
     * Returns a string representation of the cosine expression.
     *
     * @return a string representation of the cosine expression
     */
    public String toString() {
        return "cos(" + super.getOperand().toString() + ")";
    }

    /**
     * Assigns a new expression to the variable in the cosine expression.
     *
     * @param var        the variable to assign the expression to
     * @param expression the expression to assign
     * @return a new cosine expression with the updated operand
     */
    @Override
    public Expression assign(String var, Expression expression) {
        return new Cos(super.getOperand().assign(var, expression));
    }

    /**
     * Computes the derivative of the cosine expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative expression of the cosine expression
     */
    @Override
    public Expression differentiate(String var) {
        return new Neg(new Mult(new Sin(super.getOperand()), getOperand().differentiate(var)));
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
            try {
                double simpleEvaluation = simple.evaluate();
                return new Num(simpleEvaluation);
            } catch (Exception evalSimpleError) {
                return new Cos(simple);
            }
        }
    }
}
