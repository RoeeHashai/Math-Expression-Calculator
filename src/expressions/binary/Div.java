package expressions.binary;

import expressions.base.BinaryExpression;
import expressions.elements.Num;
import interfaces.Expression;

/**
 * A class that represents a division operation expression.
 */
public class Div extends BinaryExpression {
    /**
     * Constructs a division expression with the given left and right operands.
     *
     * @param leftOperand  the left operand of the division
     * @param rightOperand the right operand of the division
     */
    public Div(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Returns a string representation of the division expression.
     *
     * @return the string representation of the expression
     */
    @Override
    public String toString() {
        return "(" + super.getLeftOperand().toString() + " / " + super.getRightOperand().toString() + ")";
    }

    /**
     * Computes the derivative of the division expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative of the expression
     */
    @Override
    public Expression differentiate(String var) {
        return new Div(new Minus(new Mult(super.getLeftOperand().differentiate(var), super.getRightOperand()),
                new Mult(super.getLeftOperand(), super.getRightOperand().differentiate(var))),
                new Pow(super.getRightOperand(), new Num(2)));
    }

    /**
     * Simplifies the division expression by evaluating it if possible.
     * If evaluation is not possible, the left and right operands are simplified instead.
     *
     * @return the simplified expression
     */
    @Override
    public Expression simplify() {
        Expression leftOperandSimple, rightOperandSimple;
        double evalLOperand, evalROperand;
        try {
            double evaluated = this.evaluate();
            return new Num(evaluated);
        } catch (Exception evalExpError) {
            leftOperandSimple = super.getLeftOperand().simplify();
            rightOperandSimple = super.getRightOperand().simplify();
            if (leftOperandSimple.toString().equals(rightOperandSimple.toString())) {
                return new Num(1);
            }
            try {
                evalLOperand = leftOperandSimple.evaluate();
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    if (evalROperand == 1) {
                        return new Num(evalLOperand);
                    } else if (evalROperand == 0) {
                        return new Div(leftOperandSimple, new Num(0));
                    }
                    return new Num(evalLOperand / evalROperand);
                } catch (Exception evalROperandError) {
                    if (evalLOperand == 0) {
                        return new Num(0);
                    }
                    return new Div(new Num(evalLOperand), rightOperandSimple);
                }
            } catch (Exception evalLOperandError) {
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    if (evalROperand == 1) {
                        return leftOperandSimple;
                    } else if (evalROperand == 0) {
                        return new Div(leftOperandSimple, new Num(0));
                    }
                    return new Div(leftOperandSimple, new Num(evalROperand));
                } catch (Exception evalROperandError) {
                    return new Div(leftOperandSimple, rightOperandSimple);
                }
            }
        }
    }

    @Override
    protected double evaluateSelf(double leftOperand, double rightOperand) throws Exception {
        if (rightOperand == 0) {
            throw new Exception("Division by zero Error!");
        }
        return leftOperand / rightOperand;
    }

    @Override
    protected Expression assignSelf(Expression leftOperand, Expression rightOperand) {
        return new Div(leftOperand, rightOperand);
    }
}