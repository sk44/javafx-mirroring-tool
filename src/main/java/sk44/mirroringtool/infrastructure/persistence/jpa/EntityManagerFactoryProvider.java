/*
 */
package sk44.mirroringtool.infrastructure.persistence.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author sk
 */
class EntityManagerFactoryProvider {

    private static EntityManagerFactory factory;

    static {
        try {
            factory = Persistence.createEntityManagerFactory("mirroring-toolPU");
        } catch (ExceptionInInitializerError e) {
            // TODO handle...
            throw e;
        }
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
