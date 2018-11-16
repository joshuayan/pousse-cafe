package poussecafe.sample;

import java.util.Set;
import poussecafe.context.BoundedContext;
import poussecafe.domain.EntityDefinition;
import poussecafe.domain.EntityImplementation;
import poussecafe.domain.Service;
import poussecafe.messaging.MessageImplementationConfiguration;
import poussecafe.messaging.internal.InternalMessaging;
import poussecafe.process.DomainProcess;
import poussecafe.sample.adapters.messaging.JacksonMessageCreated;
import poussecafe.sample.adapters.messaging.JacksonOrderCreated;
import poussecafe.sample.adapters.messaging.JacksonOrderPlaced;
import poussecafe.sample.adapters.messaging.JacksonOrderReadyForShipping;
import poussecafe.sample.adapters.messaging.JacksonOrderRejected;
import poussecafe.sample.adapters.messaging.JacksonOrderSettled;
import poussecafe.sample.adapters.storage.CustomerData;
import poussecafe.sample.adapters.storage.CustomerDataAccess;
import poussecafe.sample.adapters.storage.MessageInternalDataAccess;
import poussecafe.sample.adapters.storage.MessageData;
import poussecafe.sample.adapters.storage.OrderData;
import poussecafe.sample.adapters.storage.OrderDataAccess;
import poussecafe.sample.adapters.storage.ProductData;
import poussecafe.sample.adapters.storage.ProductDataAccess;
import poussecafe.sample.domain.ContentChooser;
import poussecafe.sample.domain.Customer;
import poussecafe.sample.domain.CustomerFactory;
import poussecafe.sample.domain.CustomerRepository;
import poussecafe.sample.domain.Message;
import poussecafe.sample.domain.MessageCreated;
import poussecafe.sample.domain.MessageFactory;
import poussecafe.sample.domain.MessageRepository;
import poussecafe.sample.domain.Order;
import poussecafe.sample.domain.OrderCreated;
import poussecafe.sample.domain.OrderFactory;
import poussecafe.sample.domain.OrderPlaced;
import poussecafe.sample.domain.OrderReadyForShipping;
import poussecafe.sample.domain.OrderRejected;
import poussecafe.sample.domain.OrderRepository;
import poussecafe.sample.domain.OrderSettled;
import poussecafe.sample.domain.Product;
import poussecafe.sample.domain.ProductFactory;
import poussecafe.sample.domain.ProductRepository;
import poussecafe.sample.process.CustomerCreation;
import poussecafe.sample.process.Messaging;
import poussecafe.sample.process.OrderPlacement;
import poussecafe.sample.process.ProductManagement;
import poussecafe.storage.internal.InternalStorage;
import poussecafe.util.IdGenerator;

public class SampleMetaAppBoundedContext extends BoundedContext {

    @Override
    protected void loadDefinitions(Set<EntityDefinition> definitions) {
        definitions.add(new EntityDefinition.Builder()
                .withEntityClass(Customer.class)
                .withFactoryClass(CustomerFactory.class)
                .withRepositoryClass(CustomerRepository.class)
                .build());
        definitions.add(new EntityDefinition.Builder()
                .withEntityClass(Message.class)
                .withFactoryClass(MessageFactory.class)
                .withRepositoryClass(MessageRepository.class)
                .build());
        definitions.add(new EntityDefinition.Builder()
                .withEntityClass(Product.class)
                .withFactoryClass(ProductFactory.class)
                .withRepositoryClass(ProductRepository.class)
                .build());
        definitions.add(new EntityDefinition.Builder()
                .withEntityClass(Order.class)
                .withFactoryClass(OrderFactory.class)
                .withRepositoryClass(OrderRepository.class)
                .build());
    }

    @Override
    protected void loadEntityImplementations(Set<EntityImplementation> implementations) {
        implementations.add(new EntityImplementation.Builder()
                .withEntityClass(Customer.class)
                .withDataFactory(CustomerData::new)
                .withDataAccessFactory(CustomerDataAccess::new)
                .withStorage(InternalStorage.instance())
                .build());
        implementations.add(new EntityImplementation.Builder()
                .withEntityClass(Message.class)
                .withDataAccessFactory(MessageInternalDataAccess::new)
                .withDataFactory(MessageData::new)
                .withStorage(InternalStorage.instance())
                .build());
        implementations.add(new EntityImplementation.Builder()
                .withEntityClass(Product.class)
                .withDataFactory(ProductData::new)
                .withDataAccessFactory(ProductDataAccess::new)
                .withStorage(InternalStorage.instance())
                .build());
        implementations.add(new EntityImplementation.Builder()
                .withEntityClass(Order.class)
                .withDataFactory(OrderData::new)
                .withDataAccessFactory(OrderDataAccess::new)
                .withStorage(InternalStorage.instance())
                .build());
    }

    @Override
    protected void loadMessageImplementations(Set<MessageImplementationConfiguration> implementations) {
        implementations.add(new MessageImplementationConfiguration.Builder()
                .withMessageClass(MessageCreated.class)
                .withMessageImplementationClass(JacksonMessageCreated.class)
                .withMessaging(InternalMessaging.instance())
                .build());

        implementations.add(new MessageImplementationConfiguration.Builder()
                .withMessageClass(OrderCreated.class)
                .withMessageImplementationClass(JacksonOrderCreated.class)
                .withMessaging(InternalMessaging.instance())
                .build());

        implementations.add(new MessageImplementationConfiguration.Builder()
                .withMessageClass(OrderPlaced.class)
                .withMessageImplementationClass(JacksonOrderPlaced.class)
                .withMessaging(InternalMessaging.instance())
                .build());

        implementations.add(new MessageImplementationConfiguration.Builder()
                .withMessageClass(OrderReadyForShipping.class)
                .withMessageImplementationClass(JacksonOrderReadyForShipping.class)
                .withMessaging(InternalMessaging.instance())
                .build());

        implementations.add(new MessageImplementationConfiguration.Builder()
                .withMessageClass(OrderRejected.class)
                .withMessageImplementationClass(JacksonOrderRejected.class)
                .withMessaging(InternalMessaging.instance())
                .build());

        implementations.add(new MessageImplementationConfiguration.Builder()
                .withMessageClass(OrderSettled.class)
                .withMessageImplementationClass(JacksonOrderSettled.class)
                .withMessaging(InternalMessaging.instance())
                .build());
    }

    @Override
    protected void loadProcesses(Set<Class<? extends DomainProcess>> processes) {
        processes.add(ProductManagement.class);
        processes.add(Messaging.class);
        processes.add(CustomerCreation.class);
        processes.add(OrderPlacement.class);
    }

    @Override
    protected void loadServices(Set<Class<? extends Service>> services) {
        services.add(ContentChooser.class);
        services.add(IdGenerator.class);
    }

}
