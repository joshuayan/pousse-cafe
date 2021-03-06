package poussecafe.doc.model.processstepdoc;

import java.util.List;
import poussecafe.doc.model.boundedcontextdoc.BoundedContextDocId;
import poussecafe.doc.model.processstepdoc.ProcessStepDoc.Attributes;
import poussecafe.domain.Repository;

public class ProcessStepDocRepository extends Repository<ProcessStepDoc, ProcessStepDocId, ProcessStepDoc.Attributes> {

    public List<ProcessStepDoc> findByDomainProcess(BoundedContextDocId boundedContextDocId,
            String processName) {
        return wrap(dataAccess().findByDomainProcess(boundedContextDocId, processName));
    }

    @Override
    public ProcessStepDataAccess<ProcessStepDoc.Attributes> dataAccess() {
        return (ProcessStepDataAccess<Attributes>) super.dataAccess();
    }

    public List<ProcessStepDoc> findConsuming(BoundedContextDocId boundedContextDocId,
            String eventName) {
        return wrap(dataAccess().findConsuming(boundedContextDocId, eventName));
    }

    public List<ProcessStepDoc> findProducing(BoundedContextDocId boundedContextDocId,
            String eventName) {
        return wrap(dataAccess().findProducing(boundedContextDocId, eventName));
    }
}
