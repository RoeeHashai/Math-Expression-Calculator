package expressions.base;

import interfaces.Expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * An abstract class that represents a binary expression that extends the BaseExpression class.
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression leftOperand;
    private Expression rightOperand;

    /**
     * Constructs a binary expression with the given left and right operands.
     *
     * @param leftOperand  the left operand of the binary expression
     * @param rightOperand the right operand of the binary expression
     */
    public BinaryExpression(Expression leftOperand, Expression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    /**
     * Return's the variables used in the binary expression.
     *
     * @return a list of variable names used in the expression
     */
    @Override
    public List<String> getVariables() {
        List<String> lOperandVar = leftOperand.getVariables();
        List<String> rOperandVar = rightOperand.getVariables();
        List<String> var = new ArrayList<>(lOperandVar);
        var.addAll(rOperandVar);
        HashSet<String> varSet = new HashSet<>(var);
        var.clear();
        var.addAll(varSet);
        return var;
    }

    /**
     * Evaluates the binary expression with the given variable assignments.
     *
     * @param assignment a map of variable assignments
     * @return the result of the evaluation
     * @throws Exception if an error occurs during evaluation
     */
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double leftOperandValue = getLeftOperand().evaluate(assignment);
        double rightOperandValue = getRightOperand().evaluate(assignment);
        return evaluateSelf(leftOperandValue, rightOperandValue);
    }

    /**
     * Evaluates the binary expression without a variable assignments.
     *
     * @return the result of the evaluation
     * @throws Exception if an error occurs during evaluation
     */
    @Override
    public double evaluate() throws Exception {
        double leftOperandValue = getLeftOperand().evaluate();
        double rightOperandValue = getRightOperand().evaluate();
        return evaluateSelf(leftOperandValue, rightOperandValue);
    }

    /**
     * Assigns a new expression to a variable within the binary expression.
     *
     * @param var        the variable to assign the expression to
     * @param expression the expression to assign
     * @return a new expression with the assigned variable
     */
    @Override
    public final Expression assign(String var, Expression expression) {
        Expression leftOperandAssign = getLeftOperand().assign(var, expression);
        Expression rightOperandAssign = getRightOperand().assign(var, expression);
        return assignSelf(leftOperandAssign, rightOperandAssign);
    }

    protected Expression getLeftOperand() {
        return leftOperand;
    }

    protected Expression getRightOperand() {
        return rightOperand;
    }

    protected abstract Expression assignSelf(Expression leftOperand, Expression rightOperand);

    protected abstract double evaluateSelf(double leftOperand, double rightOperand) throws Exception;
}
