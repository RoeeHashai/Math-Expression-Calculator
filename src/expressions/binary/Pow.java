package expressions.binary;

import expressions.base.BinaryExpression;
import expressions.elements.Const;
import expressions.elements.Num;
import interfaces.Expression;

/**
 * A class that represents a power operation between two expressions.
 */
public class Pow extends BinaryExpression {
    /**
     * Constructs a power expression with the given left operand and right operand.
     *
     * @param leftOperand  the left operand
     * @param rightOperand the right operand
     */
    public Pow(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Returns a string representation of the power expression.
     *
     * @return the string representation of the expression
     */
    @Override
    public String toString() {
        return "(" + super.getLeftOperand().toString() + "^" + super.getRightOperand().toString() + ")";
    }

    /**
     * Computes the derivative of the power expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative of the expression
     */
    @Override
    public Expression differentiate(String var) {
        return new Mult(new Pow(super.getLeftOperand(), super.getRightOperand()),
                new Plus(new Mult(super.getLeftOperand().differentiate(var),
                        new Div(super.getRightOperand(), super.getLeftOperand())),
                        new Mult(super.getRightOperand().differentiate(var),
                                new Log(new Const("e", 2.71), super.getLeftOperand()))));
    }


    /**
     * Simplifies the power expression by evaluating it if possible or applying simplification rules.
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
            try {
                evalLOperand = leftOperandSimple.evaluate();
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    if (evalLOperand < 0 && (evalROperand > 0
                            && evalROperand < 1 && Math.pow(evalROperand, -1) % 2 == 0)) {
                        return new Pow(leftOperandSimple, rightOperandSimple);
                    }
                    return new Num(Math.pow(evalROperand, evalLOperand));
                } catch (Exception evalROperandError) {
                    if (evalLOperand == 0) {
                        return new Num(0);
                    } else if (evalLOperand == 1) {
                        return new Num(1);
                    }
                    return new Pow(new Num(evalLOperand), rightOperandSimple);
                }
            } catch (Exception evalLOperandError) {
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    if (evalROperand == 0) {
                        return new Num(1);
                    } else if (evalROperand == 1) {
                        return leftOperandSimple;
                    }
                    return new Pow(leftOperandSimple, new Num(evalROperand));
                } catch (Exception evalROperandError) {
                    return new Pow(leftOperandSimple, rightOperandSimple);
                }
            }
        }
    }

    @Override
    protected double evaluateSelf(double leftOperand, double rightOperand) {
        return Math.pow(leftOperand, rightOperand);
    }

    @Override
    protected Expression assignSelf(Expression leftOperand, Expression rightOperand) {
        return new Pow(leftOperand, rightOperand);
    }

    @Override
    public double evaluate() throws Exception {
        double leftOperandValue = getLeftOperand().evaluate();
        double rightOperandValue = getRightOperand().evaluate();
        if (leftOperandValue < 0 && (rightOperandValue > 0
                && rightOperandValue < 1 && Math.pow(rightOperandValue, -1) % 2 == 0)) {
            throw new ArithmeticException("illegal power evaluation");
        }
        return Math.pow(leftOperandValue, rightOperandValue);

    }
}
