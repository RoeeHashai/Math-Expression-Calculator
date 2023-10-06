package expressions.binary;

import expressions.base.BinaryExpression;
import expressions.elements.Num;
import interfaces.Expression;

/**
 * A class that represents a multiplication operation between two expressions.
 */
public class Mult extends BinaryExpression {

    /**
     * Constructs a multiplication expression with the given left operand and right operand.
     *
     * @param leftOperand  the left operand
     * @param rightOperand the right operand
     */
    public Mult(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Returns a string representation of the multiplication expression.
     *
     * @return the string representation of the expression
     */
    @Override
    public String toString() {
        return "(" + super.getLeftOperand().toString() + " * " + super.getRightOperand().toString() + ")";
    }

    /**
     * Computes the derivative of the multiplication expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative of the expression
     */
    @Override
    public Expression differentiate(String var) {
        return new Plus(new Mult(super.getLeftOperand().differentiate(var), super.getRightOperand()),
                new Mult(super.getLeftOperand(), super.getRightOperand().differentiate(var)));
    }

    /**
     * Simplifies the multiplication expression by evaluating it if possible or applying simplification rules.
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
                return new Pow(leftOperandSimple, new Num(2));
            }
            try {
                evalLOperand = leftOperandSimple.evaluate();
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    return new Num(evalROperand * evalLOperand);
                } catch (Exception evalROperandError) {
                    if (evalLOperand == 0) {
                        return new Num(0);
                    } else if (evalLOperand == 1) {
                        return rightOperandSimple;
                    }
                    return new Mult(new Num(evalLOperand), rightOperandSimple);
                }
            } catch (Exception evalLOperandError) {
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    if (evalROperand == 0) {
                        return new Num(0);
                    } else if (evalROperand == 1) {
                        return leftOperandSimple;
                    }
                    return new Mult(leftOperandSimple, new Num(evalROperand));
                } catch (Exception evalROperandError) {
                    return new Mult(leftOperandSimple, rightOperandSimple);
                }
            }
        }
    }

    @Override
    protected Expression assignSelf(Expression leftOperand, Expression rightOperand) {
        return new Mult(leftOperand, rightOperand);
    }

    @Override
    protected double evaluateSelf(double leftOperand, double rightOperand) {
        if (leftOperand == 0 || rightOperand == 0) {
            return 0;
        }
        return leftOperand * rightOperand;
    }
}
