package poussecafe.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poussecafe.domain.AggregateRoot;
import poussecafe.domain.EntityData;
import poussecafe.domain.EntityDataAccess;
import poussecafe.domain.AggregateDefinition;
import poussecafe.domain.Service;
import poussecafe.exception.PousseCafeException;
import poussecafe.messaging.Message;
import poussecafe.messaging.MessageImplementation;
import poussecafe.messaging.Messaging;
import poussecafe.process.DomainProcess;
import poussecafe.storage.DataAccessImplementation;
import poussecafe.storage.DataImplementation;
import poussecafe.storage.Storage;

import static java.util.stream.Collectors.toList;

public class ClassPathExplorer {

    ClassPathExplorer(Collection<String> packagePrefix) {
        packagePrefixes = new HashSet<>(packagePrefix);
        reflections = new Reflections(packagePrefixes.toArray());
    }

    private Set<String> packagePrefixes = new HashSet<>();

    private Reflections reflections;

    @SuppressWarnings("rawtypes")
    public List<AggregateDefinition> discoverDefinitions() {
        Set<Class<?>> aggregateRootClasses = reflections.getTypesAnnotatedWith(Aggregate.class);

        List<AggregateDefinition> definitions = new ArrayList<>();
        for(Class<?> aggregateRootClass : aggregateRootClasses) {
            if(!AggregateRoot.class.isAssignableFrom(aggregateRootClass)) {
                throw new PousseCafeException("Only aggregate roots can be annotated with @" + Aggregate.class.getSimpleName());
            }

            Aggregate annotation = aggregateRootClass.getAnnotation(Aggregate.class);
            definitions.add(new AggregateDefinition.Builder()
                    .withEntityClass(aggregateRootClass)
                    .withFactoryClass(annotation.factory())
                    .withRepositoryClass(annotation.repository())
                    .build());
        }
        return definitions;
    }

    private Logger logger = LoggerFactory.getLogger(ClassPathExplorer.class);

    public List<Class<? extends Service>> discoverServices() {
        return getSubTypesOf(Service.class).collect(toList());
    }

    public List<Class<? extends DomainProcess>> discoverDomainProcesses() {
        return getSubTypesOf(DomainProcess.class).collect(toList());
    }

    public List<Class<? extends Message>> discoverMessages() {
        return getSubTypesOf(Message.class)
                .filter(Class::isInterface)
                .collect(toList());
    }

    private <C> Stream<Class<? extends C>> getSubTypesOf(Class<C> type) {
        return reflections.getSubTypesOf(type)
                .stream()
                .filter(this::inGivenPackages);
    }

    private boolean inGivenPackages(Class<?> aClass) {
        for(String packagePrefix : packagePrefixes) {
            if(aClass.getPackage().getName().startsWith(packagePrefix)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public Set<Class<Message>> getMessageImplementations(Messaging messaging) {
        Set<Class<?>> implementationClasses = reflections.getTypesAnnotatedWith(MessageImplementation.class);

        Set<Class<Message>> messageImplementationClasses = new HashSet<>();
        for(Class<?> messageImplementationClass : implementationClasses) {
            MessageImplementation annotation = messageImplementationClass.getAnnotation(MessageImplementation.class);
            if(annotation.messagingNames().length == 0 ||
                    messaging.nameIn(annotation.messagingNames())) {
                logger.debug("Adding message implementation {}", messageImplementationClass);
                messageImplementationClasses.add((Class<Message>) messageImplementationClass);
            }
        }
        return messageImplementationClasses;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Set<Class<EntityDataAccess>> getDataAccessImplementations(Storage storage) {
        Set<Class<?>> dataAccessImplementations = reflections.getTypesAnnotatedWith(DataAccessImplementation.class);
        Set<Class<?>> aggregateRoots = new HashSet<>();

        Set<Class<EntityDataAccess>> entityDataAccessClasses = new HashSet<>();
        for(Class<?> entityDataAccessClass : dataAccessImplementations) {
            DataAccessImplementation annotation = entityDataAccessClass.getAnnotation(DataAccessImplementation.class);
            if(storage.name().equals(entityDataAccessClass.getAnnotation(DataAccessImplementation.class).storageName())) {
                if(!aggregateRoots.add(annotation.aggregateRoot())) {
                    throw new PousseCafeException("Aggregate root " + annotation.aggregateRoot() + " has already an implementation");
                }

                logger.debug("Adding data access implementation {}", entityDataAccessClass);
                entityDataAccessClasses.add((Class<EntityDataAccess>) entityDataAccessClass);
            }
        }
        return entityDataAccessClasses;
    }

    @SuppressWarnings("unchecked")
    public Set<Class<EntityData<?>>> getDataImplementations(Storage storage) {
        Set<Class<?>> dataImplementations = reflections.getTypesAnnotatedWith(DataImplementation.class);

        Set<Class<EntityData<?>>> entityDataClasses = new HashSet<>();
        for(Class<?> entityDataClass : dataImplementations) {
            DataImplementation annotation = entityDataClass.getAnnotation(DataImplementation.class);
            if(annotation.storageNames().length == 0 ||
                    storage.nameIn(annotation.storageNames())) {
                logger.debug("Adding data implementation {}", entityDataClass);
                entityDataClasses.add((Class<EntityData<?>>) entityDataClass);
            }
        }
        return entityDataClasses;
    }
}