package poussecafe.doc;

import com.sun.javadoc.ClassDoc;
import java.util.function.Consumer;
import poussecafe.doc.model.boundedcontextdoc.BoundedContextDoc;
import poussecafe.doc.model.boundedcontextdoc.BoundedContextDocKey;
import poussecafe.doc.model.boundedcontextdoc.BoundedContextDocRepository;

import static poussecafe.check.Checks.checkThatValue;

public abstract class BoundedContextComponentDocCreator implements Consumer<ClassDoc> {

    public BoundedContextComponentDocCreator(RootDocWrapper rootDocWrapper) {
        checkThatValue(rootDocWrapper).notNull();
        this.rootDocWrapper = rootDocWrapper;
    }

    private RootDocWrapper rootDocWrapper;

    @Override
    public void accept(ClassDoc classDoc) {
        if (isComponentDoc(classDoc)) {
            BoundedContextDoc boundedContextDoc = boundedContextDocRepository
                    .findByPackageNamePrefixing(classDoc.qualifiedName());
            if (boundedContextDoc != null) {
                rootDocWrapper.debug("Adding " + componentName() + " with class " + classDoc.qualifiedTypeName());
                addDoc(boundedContextDoc.getKey(), classDoc);
            }
        }
    }

    protected abstract boolean isComponentDoc(ClassDoc classDoc);

    protected abstract String componentName();

    private BoundedContextDocRepository boundedContextDocRepository;

    protected abstract void addDoc(BoundedContextDocKey boundedContextDocKey,
            ClassDoc componentClassDoc);

    protected RootDocWrapper rootDocWrapper() {
        return rootDocWrapper;
    }
}