package calculator;

import calculator.units.Operand;
import calculator.units.Operator;
import calculator.units.Type;
import calculator.units.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class ExpressionParser {
    public static List<Unit> parseExpression(String expression) {
        List<Unit> parsed = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (int i = 0; i != expression.length(); ++i) {
            Character sym = expression.charAt(i);
            if (sym.equals(' ')) continue;
            if (sym.equals('(')) {
                if (!token.isEmpty()) {
                    throw new IllegalArgumentException("Invalid expression");
                }
                Unit unit = new Unit(Type.OPEN_BRACKET, String.valueOf(sym));
                parsed.add(unit);
            } else if (sym.equals(')')) {
                if (!token.isEmpty()) {
                    loadOperand(token, parsed);
                }
                Unit bracket = new Unit(Type.CLOSE_BRACKET, String.valueOf(sym));
                parsed.add(bracket);
            } else if (Set.of('+', '-', '*', '/').contains(sym)) {
                if (sym.equals('-') && token.isEmpty() && ((parsed.isEmpty() || parsed.getLast().isOpenBracket()))) {
                    parsed.add(new Operand(0));
                }
                if (!token.isEmpty()) {
                    loadOperand(token, parsed);
                }
                Unit operator = new Operator(Type.OPERATOR, String.valueOf(sym));
                parsed.add(operator);
            } else {
                if (!Character.isDigit(sym)) throw new IllegalArgumentException("Invalid character");
                token.append(sym);
            }
        }
        loadOperand(token, parsed);
        return parsed;
    }
    private static void loadOperand(StringBuilder token, List<Unit> out){
        if (token.isEmpty()) return;
        Unit digit = new Operand(Type.OPERAND, token.toString());
        token.setLength(0);
        out.add(digit);
    }
}
