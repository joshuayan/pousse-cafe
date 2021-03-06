package poussecafe.shop.adapters.messaging;

import java.io.Serializable;
import poussecafe.attribute.Attribute;
import poussecafe.attribute.AttributeBuilder;
import poussecafe.discovery.MessageImplementation;
import poussecafe.shop.domain.OrderId;
import poussecafe.shop.domain.OrderReadyForShipping;

@MessageImplementation(message = OrderReadyForShipping.class)
@SuppressWarnings("serial")
public class SerializableOrderReadyForShipping implements Serializable, OrderReadyForShipping {

    @Override
    public Attribute<OrderId> orderId() {
        return AttributeBuilder.single(OrderId.class)
                .from(SerializableOrderId.class)
                .adapt(SerializableOrderId::adapt)
                .get(() -> orderId)
                .adapt(SerializableOrderId::adapt)
                .set(value -> orderId = value)
                .build();
    }

    private SerializableOrderId orderId;
}
