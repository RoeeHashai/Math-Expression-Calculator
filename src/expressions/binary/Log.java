package expressions.binary;

import expressions.base.BinaryExpression;
import expressions.elements.Const;
import expressions.elements.Num;
import interfaces.Expression;


/**
 * A class that represents a logarithm operation expression.
 */
public class Log extends BinaryExpression {
    /**
     * Constructs a logarithm expression with the given left operand (base)
     * and right operand (expression inside the logarithm).
     *
     * @param leftOperand  the base of the logarithm
     * @param rightOperand the expression inside the logarithm
     */
    public Log(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    /**
     * Returns a string representation of the logarithm expression.
     *
     * @return the string representation of the expression
     */
    @Override
    public String toString() {
        return "log(" + super.getLeftOperand().toString() + ", " + super.getRightOperand().toString() + ")";
    }

    /**
     * Computes the derivative of the logarithm expression with respect to the given variable.
     *
     * @param var the variable to differentiate by
     * @return the derivative of the expression
     */
    @Override
    public Expression differentiate(String var) {
        Expression fx = super.getLeftOperand();
        Expression gx = super.getRightOperand();
        Expression dDxfx = super.getLeftOperand().differentiate(var);
        Expression dDxgx = super.getRightOperand().differentiate(var);
        return new Div(new Minus(new Mult(new Mult(new Log(new Const("e", 2.71), fx), fx), dDxgx),
                new Mult(new Mult(new Log(new Const("e", 2.71), gx), gx), dDxfx)),
                new Mult(new Mult(gx, fx), new Pow(new Log(new Const("e", 2.71), fx), new Num(2))));
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
                        return new Num(0);
                    }
                    return new Num(Math.log(evalROperand) / Math.log(evalLOperand));
                } catch (ArithmeticException e) {
                    return new Log(leftOperandSimple, rightOperandSimple);
                } catch (Exception evalROperandError) {
                    return new Log(new Num(evalLOperand), rightOperandSimple);
                }
            } catch (ArithmeticException e) {
                return new Log(leftOperandSimple, rightOperandSimple);
            } catch (Exception evalLOperandError) {
                try {
                    evalROperand = rightOperandSimple.evaluate();
                    if (evalROperand == 1) {
                        return new Num(0);
                    }
                    return new Log(leftOperandSimple, new Num(evalROperand));
                } catch (Exception evalROperandError) {
                    return new Log(leftOperandSimple, rightOperandSimple);
                }
            }
        }
    }

    @Override
    protected double evaluateSelf(double leftOperand, double rightOperand) throws Exception {
        if (leftOperand == 1 || leftOperand <= 0) {
            throw new ArithmeticException("Undefined Log Base!");
        }
        if (rightOperand <= 0) {
            throw new ArithmeticException("The Expression inside the Log is Undefined!");
        }
        return Math.log(rightOperand) / Math.log(leftOperand);
    }

    @Override
    protected Expression assignSelf(Expression leftOperand, Expression rightOperand) {
        return new Log(leftOperand, rightOperand);
    }
}