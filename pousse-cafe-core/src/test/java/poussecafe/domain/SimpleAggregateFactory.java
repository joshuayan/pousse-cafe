package poussecafe.domain;

import java.util.List;
import poussecafe.context.TestDomainEvent;
import poussecafe.context.TestDomainEvent2;
import poussecafe.discovery.MessageListener;

import static java.util.Arrays.asList;

public class SimpleAggregateFactory extends Factory<SimpleAggregateId, SimpleAggregate, SimpleAggregate.Attributes> {

    @MessageListener
    public SimpleAggregate newSimpleAggregate(TestDomainEvent event) {
        return newSimpleAggregate(new SimpleAggregateId("id1"));
    }

    private SimpleAggregate newSimpleAggregate(SimpleAggregateId id) {
        SimpleAggregate aggregate = newAggregateWithId(id);
        aggregate.attributes().data().value("untouched");
        return aggregate;
    }

    @MessageListener
    public List<SimpleAggregate> newSimpleAggregate(TestDomainEvent2 event) {
        return asList(newSimpleAggregate(new SimpleAggregateId("id1")),
                newSimpleAggregate(new SimpleAggregateId("id2")));
    }
}
