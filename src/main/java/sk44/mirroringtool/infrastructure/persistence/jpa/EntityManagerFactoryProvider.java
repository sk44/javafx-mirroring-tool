/*
 */
package sk44.mirroringtool.infrastructure.persistence.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sk
 */
class EntityManagerFactoryProvider {

    private static final Logger logger = LoggerFactory.getLogger(EntityManagerFactoryProvider.class);
    private static EntityManagerFactory factory;

    static {
        try {
            factory = Persistence.createEntityManagerFactory("mirroring-toolPU");
        } catch (ExceptionInInitializerError e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
