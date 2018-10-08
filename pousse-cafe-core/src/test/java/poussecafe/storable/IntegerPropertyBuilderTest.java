package poussecafe.storable;

public class IntegerPropertyBuilderTest extends NumberPropertyBuilderTest<Integer> {

    @Override
    protected NumberPropertyBuilder<Integer> builder() {
        return PropertyBuilder.number(Integer.class);
    }

    @Override
    protected Integer initialValue() {
        return 42;
    }

    @Override
    protected AddOperator<Integer> addOperator() {
        return AddOperators.INTEGER;
    }

    @Override
    protected Integer newValue() {
        return 43;
    }

    @Override
    protected Integer addTerm() {
        return 1;
    }
}