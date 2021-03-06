package poussecafe.doc.model.vodoc;

import java.io.Serializable;
import poussecafe.attribute.Attribute;
import poussecafe.doc.model.BoundedContextComponentDoc;
import poussecafe.doc.model.BoundedContextComponentDocData;

@SuppressWarnings("serial")
public class ValueObjectDocData implements ValueObjectDoc.Attributes, Serializable {

    @Override
    public Attribute<ValueObjectDocId> identifier() {
        return new Attribute<ValueObjectDocId>() {
            @Override
            public ValueObjectDocId value() {
                return ValueObjectDocId.ofClassName(className);
            }

            @Override
            public void value(ValueObjectDocId value) {
                className = value.stringValue();
            }
        };
    }

    private String className;

    @Override
    public Attribute<BoundedContextComponentDoc> boundedContextComponentDoc() {
        return new Attribute<BoundedContextComponentDoc>() {
            @Override
            public BoundedContextComponentDoc value() {
                return componentDoc.adapt();
            }

            @Override
            public void value(BoundedContextComponentDoc value) {
                componentDoc = BoundedContextComponentDocData.adapt(value);
            }
        };
    }

    private BoundedContextComponentDocData componentDoc;
}
