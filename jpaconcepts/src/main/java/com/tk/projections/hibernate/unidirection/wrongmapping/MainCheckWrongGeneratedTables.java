package com.tk.projections.hibernate.unidirection.wrongmapping;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class MainCheckWrongGeneratedTables {

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
                put(AvailableSettings.HBM2DDL_AUTO, "update");

            }
        });
        configuration.addAnnotatedClass(PostWrong.class);
        configuration.addAnnotatedClass(CommentWrong.class);

        //--------------Building SessionFactory-----------------
        SessionFactory sessionFactory =
                configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

        //----------Obtain session and call persistence methods
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        //This would generate 7 sql statements - 1 to post 3 to comments and 3 to join table
        PostWrong post = new PostWrong();
        post.setTitle("This is the Topic");

        post.getComments().add(new CommentWrong("Comment 1"));
        post.getComments().add(new CommentWrong("Comment 2"));
        post.getComments().add(new CommentWrong("Comment 3"));
        session.persist(post);

        session.getTransaction().commit();
        session.close();

    }
}
