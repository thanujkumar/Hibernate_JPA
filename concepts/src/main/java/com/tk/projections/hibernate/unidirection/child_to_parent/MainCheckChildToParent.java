package com.tk.projections.hibernate.unidirection.child_to_parent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
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
public class MainCheckChildToParent {

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
        configuration.addAnnotatedClass(PostParentC.class);
        configuration.addAnnotatedClass(CommentChildP.class);

        //--------------Building SessionFactory-----------------
        SessionFactory sessionFactory =
                configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

        //----------Obtain session and call persistence methods
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //This would generate 7 sql statements - 1 insert to post, 3 insert to comments and 3 updates to comments
        PostParentC post = new PostParentC();
        post.setTitle("This is the Topic 2");

        CommentChildP comment1 = new CommentChildP("Comment 1");
        comment1.setPost(post);
        CommentChildP comment2 = new CommentChildP("Comment 2");
        comment2.setPost(post);
        CommentChildP comment3 = new CommentChildP("Comment 3");
        comment3.setPost(post);

        session.save(post);
        session.save(comment1);
        session.save(comment2);
        session.save(comment3);

        session.getTransaction().commit();
        session.close();


        //Loading child
        session = sessionFactory.openSession();
        session.beginTransaction();

        CommentChildP comment = session.find(CommentChildP.class, 1L);
        System.out.println(comment);
        System.out.println(comment.getPost());

        session.getTransaction().commit();
        session.close();

        //Loading using JPQL
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query<CommentChildP> query = session.createQuery("SELECT C  FROM CommentChildP C JOIN FETCH C.post",
                CommentChildP.class);

        List<CommentChildP> qlist = query.getResultList();
        for (CommentChildP child : qlist) {
            System.out.println(child);
        }
        session.getTransaction().commit();
        session.close();


        //https://www.baeldung.com/hibernate-criteria-queries
        //Loading using JPQL where clause
        session = sessionFactory.openSession();
        session.beginTransaction();
        query = session.createQuery("SELECT C  FROM CommentChildP C JOIN FETCH C.post P WHERE P.id = :ID",
                CommentChildP.class);
        query.setParameter("ID", 1L);

        qlist = query.getResultList();
        for (CommentChildP child : qlist) {
            System.out.println(child);
        }
        session.getTransaction().commit();
        session.close();
    }
}
