package poussecafe.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import poussecafe.domain.EntityAttributes;
import poussecafe.exception.PousseCafeException;

public class PousseCafeTestObjectMapper {

    public PousseCafeTestObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTypingAsProperty(DefaultTyping.OBJECT_AND_NON_CONCRETE, "@class");
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.registerModule(new JavaTimeModule());
    }

    public <K, D extends EntityAttributes<K>> void readJson(D dataImplementation,
            JsonNode dataJson) {
        try {
            objectMapper.readerForUpdating(dataImplementation).readValue(dataJson);
        } catch (IOException e) {
            throw new PousseCafeException("Unable to load data implementation", e);
        }
    }

    private ObjectMapper objectMapper;

    public ObjectMapper objectMapper() {
        return objectMapper;
    }
}
