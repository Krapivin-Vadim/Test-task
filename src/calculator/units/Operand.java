package calculator.units;

public class Operand extends Unit {
    private final int value;

    public Operand(Type type, String payload) {
        super(type, payload);
        value = Integer.parseInt(payload);
    }

    public Operand(Integer value){
        super(Type.OPERAND, String.valueOf(value));
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }
}