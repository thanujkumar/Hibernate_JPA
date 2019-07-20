package com.tk.projections.hibernate.unidirection.parent_to_child;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class MainRemove {
    public static void main(String[] args) throws Exception {

        //property or xml or programmatic configuration
        Configuration configuration = new Configuration();
        configuration.setProperties(new Properties() {

            {
                //https://docs.jboss.org/hibernate/orm/5.3/javadocs/constant-values.html
                put(AvailableSettings.USER, "JPA2");
                put(AvailableSettings.PASS, "app");
                put(AvailableSettings.URL, "jdbc:oracle:thin:@localhost:1521/orcl");
                put(AvailableSettings.DRIVER, "oracle.jdbc.driver.OracleDriver");
                put(AvailableSettings.DIALECT, "org.hibernate.dialect.Oracle12cDialect");
                put(AvailableSettings.DEFAULT_SCHEMA, "JPA2");
                put(AvailableSettings.SHOW_SQL, Boolean.TRUE.booleanValue());
                put(AvailableSettings.FORMAT_SQL, true);
                put(AvailableSettings.GENERATE_STATISTICS, true);
                put(AvailableSettings.FAIL_ON_PAGINATION_OVER_COLLECTION_FETCH, true);
            }
        });
        configuration.addAnnotatedClass(PostParent.class);
        configuration.addAnnotatedClass(CommentChild.class);

        //--------------Building SessionFactory-----------------
        SessionFactory sessionFactory =
                configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

        //----------Obtain session and call persistence methods
        Session session = sessionFactory.openSession();
        session.beginTransaction();
         //1 statment to read post and another statement to read comments
        PostParent postFixed =  session.find(PostParent.class, 1L);
        //1 statment to delete
        postFixed.getComments().remove(0);

        session.getTransaction().commit();
        session.close();

    }
}
