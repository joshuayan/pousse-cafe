package poussecafe.messaging;

import org.junit.Test;
import poussecafe.runtime.MessageConsumer;
import poussecafe.runtime.OriginalAndMarshaledMessage;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public abstract class MessageReceiverTest {

    private Message message;

    private MessageConsumer messageConsumer;

    @Test
    public void receivedMessageIsConsumed() {
        givenMessageConsumer();
        whenConsumingMessage();
        thenListenerConsumes();
    }

    private void givenMessageConsumer() {
        message = message();
        messageConsumer = mock(MessageConsumer.class);
        connection = messaging().connect(messageConsumer);
    }

    protected abstract Message message();

    protected abstract Messaging messaging();

    private MessagingConnection connection;

    private void whenConsumingMessage() {
        serializedMessage = serializedMessage(message);
        connection.messageReceiver().onMessage(serializedMessage);
    }

    private Object serializedMessage;

    protected abstract Object serializedMessage(Message message);

    private void thenListenerConsumes() {
        verify(messageConsumer).consumeMessage(new OriginalAndMarshaledMessage.Builder()
                .marshaled(serializedMessage)
                .original(message)
                .build());
    }
}
