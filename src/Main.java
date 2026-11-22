import calculator.Calculator;

import java.util.List;

public class Main {

    static void main() {
        for (TestCase test : CASES){
            boolean exception = test.expected == null;
            try{
                assert test.expected.equals(Calculator.evaluate(test.expression));
            }
            catch (IllegalArgumentException e){
                if (!exception){
                    throw new AssertionError(test.expression);
                }
            }
            catch (AssertionError e) {
                System.out.println(test.expression);
            }
        }
    }

    record TestCase(String expression, Integer expected) {}

    static final List<TestCase> CASES = List.of(
            new TestCase("2+3*4",        14),
            new TestCase("(2+3)*4",      20),
            new TestCase("10/2-3",       2),
            new TestCase("2*(3+4)-5",    9),
            new TestCase("2 + 2",        4),
            new TestCase("10/0",         null),
            new TestCase("2+",           null),
            new TestCase("(2+3",         null),
            new TestCase("2+a",          null),
            new TestCase("2",            2),
            new TestCase(")",            null),
            new TestCase("-",            null),
            new TestCase("10 0 + 1",     101),
            new TestCase("-1 + 2",       1),
            new TestCase("(-3 + 1) + 1", -1),
            new TestCase("2*(-3)", -6),
            new TestCase("2*-3", null),
            new TestCase("5+", null),
            new TestCase("1+*2", null),
            new TestCase("(1+2)(3)", null),
            new TestCase("2(3)", null),
            new TestCase("(2)", 2),
            new TestCase("-5+3", -2),
            new TestCase("2*(-3)", -6),
            new TestCase("(-5)*2", -10),
            new TestCase("-(2+3)", -5)
    );
}
