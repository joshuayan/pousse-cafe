package poussecafe.doc.model.servicedoc;

import java.util.List;
import poussecafe.doc.model.boundedcontextdoc.BoundedContextDocKey;
import poussecafe.storage.memory.InMemoryDataAccess;

import static java.util.stream.Collectors.toList;

public class InMemoryServiceDocDataAccess extends InMemoryDataAccess<ServiceDocKey, ServiceDocData> implements ServiceDocDataAccess<ServiceDocData> {

    @Override
    public List<ServiceDocData> findByBoundedContextKey(BoundedContextDocKey key) {
        return findAll().stream().filter(data -> data.boundedContextComponentDoc().get().boundedContextDocKey().equals(key)).collect(toList());
    }

}
