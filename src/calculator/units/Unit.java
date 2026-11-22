package calculator.units;

public class Unit {
    private final Type type;
    protected final String payload;

    public Unit(Type type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    public final boolean isOperand(){
        return type.equals(Type.OPERAND);
    }

    public final boolean isOperator(){
        return type.equals(Type.OPERATOR);
    }

    public final boolean isOpenBracket() {
        return type.equals(Type.OPEN_BRACKET);
    }

    public final boolean isCloseBracket(){
        return type.equals(Type.CLOSE_BRACKET);
    }

    public final String getPayload(){
        return payload;
    }
}