package poussecafe.doc.model.boundedcontextdoc;

import poussecafe.domain.EntityDataAccess;

public interface BoundedContextDocDataAccess<D extends BoundedContextDoc.Attributes> extends EntityDataAccess<BoundedContextDocId, D> {

    D findByPackageNamePrefixing(String name);

}
