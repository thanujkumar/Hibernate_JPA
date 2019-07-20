package com.tk.projections.hibernate.unidirection.parent_to_child;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

/**
 * A JPA entity may be in one of the following states:
 *
 * <ul>
 * <li><b>New/Transient</b>: the entity is not associated with a persistence context, be it a newly created object the database doesn’t know anything about.
 * <li><b>Persistent</b>: the entity is associated with a persistence context (residing in the 1st Level Cache) and there is a database row representing this entity.
 * <li><b>Detached</b>: the entity was previously associated with a persistence context, but the persistence context was closed, or the entity was manually evicted.
 * <li><b>Removed</b>: the entity was marked as removed and the persistence context will remove it from the database at flush time.
 * </ul>
 * <p>
 * Moving an object from one state to another is done by calling the EntityManager methods such as:
 * <ul>
 * <li>persist
 * <li>merge
 * <li>remove
 * </ul>
 * </p>
 */
public class MainCheckParentToChild {

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
                put(AvailableSettings.HBM2DDL_AUTO, "create-drop");//create-drop

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

        //This would generate 7 sql statements - 1 insert to post, 3 insert to comments and 3 updates to comments
        PostParent post = new PostParent();
        post.setTitle("This is the Topic 2");

        post.getComments().add(new CommentChild("Comment 1"));
        post.getComments().add(new CommentChild("Comment 2"));
        post.getComments().add(new CommentChild("Comment 3"));
        session.save(post);

        session.getTransaction().commit();
        session.close();

    }
}
