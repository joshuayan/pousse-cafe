package poussecafe.doc.model.aggregatedoc;

import java.util.List;
import poussecafe.discovery.DataAccessImplementation;
import poussecafe.doc.model.boundedcontextdoc.BoundedContextDocId;
import poussecafe.storage.internal.InternalDataAccess;
import poussecafe.storage.internal.InternalStorage;

import static java.util.stream.Collectors.toList;

@DataAccessImplementation(
    aggregateRoot = AggregateDoc.class,
    dataImplementation = AggregateDocData.class,
    storageName = InternalStorage.NAME
)
public class InternalAggregateDocDataAccess extends InternalDataAccess<AggregateDocId, AggregateDocData> implements AggregateDocDataAccess<AggregateDocData> {

    @Override
    public List<AggregateDocData> findByBoundedContextId(BoundedContextDocId id) {
        return findAll().stream().filter(data -> data.boundedContextComponentDoc().value().boundedContextDocId().equals(id)).collect(toList());
    }

    @Override
    public List<AggregateDocData> findByIdClassName(String qualifiedName) {
        return findAll().stream().filter(data -> data.idClassName().value().equals(qualifiedName)).collect(toList());
    }

    @Override
    public AggregateDocData findByBoundedContextIdAndName(BoundedContextDocId boundedContextDocId,
            String aggregateName) {
        return findAll().stream()
                .filter(data -> data.boundedContextComponentDoc().value().boundedContextDocId().equals(boundedContextDocId))
                .filter(data -> data.boundedContextComponentDoc().value().componentDoc().name().equals(aggregateName))
                .findFirst().orElse(null);
    }

}
