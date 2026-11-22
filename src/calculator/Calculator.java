package calculator;

import calculator.units.Operand;
import calculator.units.Operator;
import calculator.units.Unit;

import java.util.*;

public class Calculator {
    public static int evaluate(String expression){
        List<Unit> parsed = ExpressionParser.parseExpression(expression);
        List<Unit> postfix = translateToPostfix(parsed);

        Deque<Operand> stack = new ArrayDeque<>();
        for (Unit unit : postfix){
            if (unit.isOperand()) stack.push((Operand) unit);
            else if (unit.isOperator()){
                if (stack.size() < 2) throw new IllegalArgumentException(
                        "Not enough operands for '%s' operator".formatted(unit.getPayload()));
                Operand operand1 = stack.pop();
                Operand operand2 = stack.pop();
                Operator operator = (Operator) unit;
                Operand newOperand = new Operand(operator.calculate(
                                List.of(operand2.getValue(), operand1.getValue())));
                stack.push(newOperand);
            }
        }
        if (stack.size() != 1) throw new IllegalArgumentException("Invalid expression");
        return stack.peek().getValue();
    }

    private static List<Unit> translateToPostfix(List<Unit> infixForm){
        Deque<Unit> buffer = new ArrayDeque<>();
        List<Unit> output = new ArrayList<>(infixForm.size());
        for(Unit unit : infixForm){
            if (unit.isOperand()) output.add(unit);

            else if (unit.isOpenBracket()) buffer.push(unit);

            else if (unit.isCloseBracket()){
                while (!buffer.isEmpty() && !buffer.peek().isOpenBracket()){
                    output.add(buffer.pop());
                }
                if (buffer.isEmpty()){
                    throw new IllegalArgumentException("Invalid brackets position");
                }
                buffer.pop();
            } else if (unit.isOperator()) {
                Operator unitOperator = (Operator) unit;
                while (!buffer.isEmpty() && buffer.peek().isOperator()){
                    Operator stackOperator = (Operator) buffer.peek();
                    if (stackOperator.getPriority() < unitOperator.getPriority()){
                        break;
                    }
                    output.add(buffer.pop());
                }
                buffer.push(unit);
            }
        }
        while (!buffer.isEmpty()){
            var unit = buffer.pop();
            if (unit.isCloseBracket() || unit.isOpenBracket()){
                throw new IllegalArgumentException("Invalid brackets position");
            }
            output.add(unit);
        }
        return output;
    }
}
