package de.cit.backend.mgmt.business;

import java.lang.reflect.Field;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.InjectionException;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Klasse ist eine CDI-Erweiterung, die einen EntityManager erzeugt und in alle DAOs injiziert. Auf diese Weise koennen die Services ausserhalb des EJB-Containers getestet werden.
 * 
 * @author Sven Carlin
 * 
 */
public class CdiTestExtension implements Extension {

    public static final String CONN_URI = "jdbc:h2:mem:test";
    public static final String PERSISTENCE_UNIT_NAME = "bitflow-backend-mgmt-test";
    public static final String FIELD_NAME_ENTITYMANAGER = "entityManager";
    
    public static final String CLASS_NAME_PERSISTENCE_SERVICE = "PersistenceService";
    
    /**
     * Singleton eines EntityManagers
     */
    private static EntityManager emInstance;
    
    /**
     * Liefert einen EntityManger, erzeugt ggf. diesen zuvor.
     * 
     * @return den EintityManager
     */
    public EntityManager getEntityManager() {
        synchronized (CdiTestExtension.class) {
            if (emInstance == null) {
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                emInstance = entityManagerFactory.createEntityManager();
            }
            return emInstance;
        }
    }
    
    <X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> pit) {

        String name = pit.getAnnotatedType().getJavaClass().getName();
        if(isEntityManagerInjection(name)){
            injectEntityManager(pit);
        }
    }

    <X> void injectEntityManager(ProcessInjectionTarget<X> pit) {
        final InjectionTarget<X> it = pit.getInjectionTarget();
        try {
            
            AnnotatedType<X> at = pit.getAnnotatedType();
            final Field field = at.getJavaClass().getDeclaredField(FIELD_NAME_ENTITYMANAGER);
            final Object value = getEntityManager();
            field.setAccessible(true);
            //Feld holen, entityManager erzeugen un target wrappen
            
            pit.setInjectionTarget(getInjectionTarget(it, field, value));
            
        } catch (NoSuchFieldException nsfe) {
            //wenn kein Feld mit Name 'entityManager' existiert, wird auch keiner injiziert, aber auch kein Fehler geworfen
        }
    }

    private <X> InjectionTarget<X> getInjectionTarget(final InjectionTarget<X> it, final Field field, final Object value) {
        InjectionTarget<X> wrapped = new InjectionTarget<X>() {

            @Override
            public void inject(X instance, CreationalContext<X> ctx) {
                try {
                    it.inject(instance, ctx);
                    field.set(instance, value);

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new InjectionException(e);
                }
            }

            @Override
            public void postConstruct(X instance) {

                it.postConstruct(instance);
            }

            @Override
            public void preDestroy(X instance) {

                it.dispose(instance);
            }

            @Override
            public void dispose(X instance) {

                it.dispose(instance);
            }

            @Override
            public Set<InjectionPoint> getInjectionPoints() {

                return it.getInjectionPoints();
            }

            @Override
            public X produce(CreationalContext<X> ctx) {

                return it.produce(ctx);
            }
        };
        
        return wrapped;
    }

    private boolean isEntityManagerInjection(String name) {
        return name.endsWith(CLASS_NAME_PERSISTENCE_SERVICE);
    }
}
