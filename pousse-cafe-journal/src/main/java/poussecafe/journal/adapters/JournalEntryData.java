package poussecafe.journal.adapters;

import java.io.Serializable;
import poussecafe.attribute.Attribute;
import poussecafe.attribute.AttributeBuilder;
import poussecafe.journal.domain.ConsumptionStatus;
import poussecafe.journal.domain.JournalEntry;
import poussecafe.journal.domain.JournalEntryId;

@SuppressWarnings("serial")
public class JournalEntryData implements JournalEntry.Attributes, Serializable {

    @Override
    public Attribute<JournalEntryId> identifier() {
        return AttributeBuilder.single(JournalEntryId.class)
                .fromAutoAdapting(SerializableJournalEntryId.class)
                .get(() -> id)
                .set(value -> id = value)
                .build();
    }

    private SerializableJournalEntryId id;

    @Override
    public Attribute<String> rawMessage() {
        return AttributeBuilder.single(String.class)
                .get(() -> rawMessage)
                .set(value -> rawMessage = value)
                .build();
    }

    private String rawMessage;

    @Override
    public Attribute<String> error() {
        return AttributeBuilder.single(String.class)
                .get(() -> error)
                .set(value -> error = value)
                .build();
    }

    private String error;

    @Override
    public Attribute<ConsumptionStatus> status() {
        return AttributeBuilder.single(ConsumptionStatus.class)
                .get(() -> status)
                .set(value -> status = value)
                .build();
    }

    private ConsumptionStatus status;
}
