package poussecafe.myboundedcontext.adapters;

import java.io.Serializable;
import poussecafe.attribute.Attribute;
import poussecafe.attribute.AttributeBuilder;
import poussecafe.myboundedcontext.domain.myaggregate.MyAggregate;
import poussecafe.myboundedcontext.domain.myaggregate.MyAggregateId;

@SuppressWarnings("serial")
public class MyAggregateData implements MyAggregate.Attributes, Serializable {

    /*
     * AttributeBuilder class exposes factory methods for different types of property builders. The property
     * builders allow to directly expose the value or adapt it before.
     */
    @Override
    public Attribute<MyAggregateId> identifier() {
        return AttributeBuilder.stringId(MyAggregateId.class)
                .get(() -> id)
                .set(value -> id = value)
                .build();
    }

    private String id;

    @Override
    public Attribute<Integer> x() {
        return AttributeBuilder.single(Integer.class)
                .get(() -> x)
                .set(value -> x = value)
                .build();
    }

    private int x;
}
