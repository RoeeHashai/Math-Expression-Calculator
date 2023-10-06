package expressions.binary;

import expressions.base.BinaryExpression;
import expressions.elements.Num;
import interfaces.Expression;

/**
 * A class that represents a addition operation between two expressions.
 */
public class Plus extends BinaryExpression {

    /**
     * Constructs an addition expression with the given left operand and right operand.
     *
     * @param leftOperand  the left operand
     * @param rightOperand the right operand
     */
    public Plus(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }


    /**
     * Returns a string representation of the addition expression.
     *
     * @return the string representation of the expression
     */
    @Override
    public String toString() {
        return "(" + super.getLeftOperand().toString() + " + " + super.getRightOperand().toString() + ")";
    }

    /**
     * Computes the derivative of the addition expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative of the expression
     */
    @Override
    public Expression differentiate(String var) {
        return new Plus(super.getLeftOperand().differentiate(var), super.getRightOperand().differentiate(var));
    }

    /**
     * Simplifies the addition expression by evaluating it if possible or applying simplification rules.
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
                return new Mult(new Num(2), leftOperandSimple);
            }
            try {
                evalLOperand = leftOperandSimple.evaluate();
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    return new Num(evalROperand + evalLOperand);

                } catch (Exception evalROperandError) {
                    if (evalLOperand == 0) {
                        return rightOperandSimple;
                    }
                    return new Plus(new Num(evalLOperand), rightOperandSimple);
                }
            } catch (Exception evalLOperandError) {
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    if (evalROperand == 0) {
                        return leftOperandSimple;
                    }
                    return new Plus(leftOperandSimple, new Num(evalROperand));
                } catch (Exception evalROperandError) {
                    return new Plus(leftOperandSimple, rightOperandSimple);
                }
            }
        }
    }

    @Override
    protected double evaluateSelf(double leftOperand, double rightOperand) {
        return leftOperand + rightOperand;
    }

    @Override
    protected Expression assignSelf(Expression leftOperand, Expression rightOperand) {
        return new Plus(leftOperand, rightOperand);
    }
}
