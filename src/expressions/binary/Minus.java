package expressions.binary;

import expressions.base.BinaryExpression;
import expressions.elements.Num;
import expressions.unary.Neg;
import interfaces.Expression;

/**
 * A class that represents a subtraction operation between two expressions.
 */
public class Minus extends BinaryExpression {
    /**
     * Constructs a subtraction expression with the given left operand and right operand.
     *
     * @param leftOperand  the left operand
     * @param rightOperand the right operand
     */
    public Minus(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Returns a string representation of the logarithm expression.
     *
     * @return the string representation of the expression
     */
    @Override
    public String toString() {
        return "(" + super.getLeftOperand().toString() + " - " + super.getRightOperand().toString() + ")";
    }


    /**
     * Computes the derivative of the subtraction expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative of the expression
     */
    @Override
    public Expression differentiate(String var) {
        return new Minus(super.getLeftOperand().differentiate(var), super.getRightOperand().differentiate(var));
    }

    /**
     * Simplifies the subtraction expression by evaluating it if possible.
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
            if (rightOperandSimple instanceof Neg) {
                return (new Plus(leftOperandSimple, ((Neg) rightOperandSimple).getOperand())).simplify();
            }
            if (leftOperandSimple.toString().equals(rightOperandSimple.toString())) {
                return new Num(0);
            }
            try {
                evalLOperand = leftOperandSimple.evaluate();
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    return new Num(evalLOperand - evalROperand);
                } catch (Exception evalROperandError) {
                    if (evalLOperand == 0) {
                        return new Neg(rightOperandSimple);
                    }
                    return new Minus(new Num(evalLOperand), rightOperandSimple);
                }
            } catch (Exception evalLOperandError) {
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    if (evalROperand == 0) {
                        return leftOperandSimple;
                    }
                    return new Minus(leftOperandSimple, new Num(evalROperand));
                } catch (Exception evalROperandError) {
                    return new Minus(leftOperandSimple, rightOperandSimple);
                }
            }
        }
    }

    @Override
    protected Expression assignSelf(Expression leftOperand, Expression rightOperand) {
        return new Minus(leftOperand, rightOperand);
    }

    @Override
    protected double evaluateSelf(double leftOperand, double rightOperand) {
        return leftOperand - rightOperand;
    }
}