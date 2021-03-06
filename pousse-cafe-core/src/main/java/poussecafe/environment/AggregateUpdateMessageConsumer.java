package poussecafe.environment;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import poussecafe.domain.AggregateRoot;
import poussecafe.domain.Repository;
import poussecafe.messaging.Message;
import poussecafe.runtime.TransactionRunnerLocator;
import poussecafe.storage.TransactionRunner;
import poussecafe.util.MethodInvoker;

@SuppressWarnings({"unchecked", "rawtypes"})
public class AggregateUpdateMessageConsumer implements Consumer<Message> {

    public static class Builder {

        private AggregateUpdateMessageConsumer consumer = new AggregateUpdateMessageConsumer();

        public Builder method(Method method) {
            consumer.method = method;
            return this;
        }

        public Builder transactionRunnerLocator(TransactionRunnerLocator transactionRunnerLocator) {
            consumer.transactionRunnerLocator = transactionRunnerLocator;
            return this;
        }

        public Builder aggregateServices(AggregateServices aggregateServices) {
            consumer.aggregateServices = aggregateServices;
            return this;
        }

        public Builder runner(AggregateMessageListenerRunner runner) {
            consumer.runner = runner;
            return this;
        }

        public AggregateUpdateMessageConsumer build() {
            Objects.requireNonNull(consumer.transactionRunnerLocator);
            Objects.requireNonNull(consumer.aggregateServices);
            Objects.requireNonNull(consumer.method);
            return consumer;
        }
    }

    private AggregateUpdateMessageConsumer() {

    }

    @Override
    public void accept(Message message) {
        Set targetAggregatesId = runner.targetAggregatesIds(message);
        Repository repository = aggregateServices.repository();
        Class entityClass = aggregateServices.aggregateRootEntityClass();
        for(Object id : targetAggregatesId) {
            TransactionRunner transactionRunner = transactionRunnerLocator.locateTransactionRunner(entityClass);
            transactionRunner.runInTransaction(() -> {
                AggregateRoot targetAggregateRoot = repository.get(id);
                targetAggregateRoot.context(runner.context(message, targetAggregateRoot));
                MethodInvoker invoker = new MethodInvoker.Builder()
                        .method(method)
                        .target(targetAggregateRoot)
                        .build();
                invoker.invoke(message);
                repository.update(targetAggregateRoot);
            });
        }
    }

    private AggregateMessageListenerRunner runner;

    private Method method;

    private TransactionRunnerLocator transactionRunnerLocator;

    private AggregateServices aggregateServices;
}
