package com.tk.projections.hibernate;

import antlr.collections.impl.IntRange;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;
import java.util.Random;
import java.util.stream.IntStream;

public class MainGenerateData {
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
        configuration.addAnnotatedClass(Post.class);
        configuration.addAnnotatedClass(Comment.class);

        //--------------Building SessionFactory-----------------
        SessionFactory sessionFactory =
                configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());

        //----------Obtain session and call persistence methods
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Random rand = new Random();
        //Post
        IntStream.range(1, 5).forEach(p -> {
            Post post = new Post();
            post.setTitle("About Projections-"+p);
            post.setContent("This example is about projects in hibernate-"+p);
            post.setDescription("Hibernate projects-"+p);
            session.save(post);
            //Generate 5 comments
            IntStream.range(1, rand.nextInt(10)).forEach(x -> {
                Comment comment = new Comment();
                comment.setPost(post);
                comment.setText("Comment"+x);
                session.save(comment);
            });

        });


        session.getTransaction().commit();
        session.close();

    }
}
