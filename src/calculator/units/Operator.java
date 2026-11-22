package calculator.units;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Operator extends Unit{

    private final Function<List<Integer>, Integer> function;
    private final Integer priority;

    private static final Map<String, Function<List<Integer>, Integer>> functionLib = Map.of(
            "-", (List<Integer> operands) -> operands.getFirst() - operands.getLast(),
            "+", (List<Integer> operands) -> operands.getFirst() + operands.getLast(),
            "*", (List<Integer> operands) -> operands.getFirst() * operands.getLast(),
            "/", (List<Integer> operands) -> {
                Integer del = operands.getLast();
                if (del.equals(0)){
                    throw new IllegalArgumentException("Division by zero");
                }
                return operands.getFirst() / del;
            }
    );

    private static final Map<String, Integer> priorityOrder = Map.of(
            "+", 0,
            "-", 0,
            "/", 1,
            "*", 1
    );

    public Operator(Type type, String payload) {
        super(type, payload);
        if (!functionLib.containsKey(payload)){
            throw new IllegalArgumentException("Unacceptable operator symbol");
        }
        function = functionLib.get(payload);
        priority = priorityOrder.get(payload);
    }

    final public Integer calculate(List<Integer> operands){
        return function.apply(operands);
    }

    final public Integer getPriority(){
        return priority;
    }
}
