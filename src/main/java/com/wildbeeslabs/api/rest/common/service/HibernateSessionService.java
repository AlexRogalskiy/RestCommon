package com.wildbeeslabs.api.rest.common.service;

//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
//import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * Hibernate Session REST Application Service implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Transactional
public class HibernateSessionService {

    private static final SessionFactory DEFAULT_SESSSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            /*Configuration configuration = new Configuration().configure();

                ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
                registry.applySettings(configuration.getProperties());
                ServiceRegistry serviceRegistry = registry.buildServiceRegistry();

                return configuration.buildSessionFactory(serviceRegistry);*/
            return new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return HibernateSessionService.DEFAULT_SESSSION_FACTORY;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
