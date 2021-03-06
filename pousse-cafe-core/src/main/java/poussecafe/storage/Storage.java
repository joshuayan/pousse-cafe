package poussecafe.storage;

public abstract class Storage {

    public MessageSendingPolicy getMessageSendingPolicy() {
        return policy;
    }

    private MessageSendingPolicy policy = initMessageSendingPolicy();

    protected abstract MessageSendingPolicy initMessageSendingPolicy();

    public TransactionRunner getTransactionRunner() {
        return transactionRunner;
    }

    private TransactionRunner transactionRunner = initTransactionRunner();

    protected abstract TransactionRunner initTransactionRunner();

    public abstract String name();

    public boolean nameIn(String[] array) {
        for(String name : array) {
            if(name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
