package com.tk.projections.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Properties;

//https://www.baeldung.com/hibernate-criteria-queries
//https://www.javacodegeeks.com/2018/04/jpa-tips-avoiding-the-n-1-select-problem.html
public class MainProjection {

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

        //No projects but typed query
        //SELECT P FROM Post P JOIN FETCH P.comments WHERE P.id = :POST_ID
        TypedQuery<Post> jpaQuery = session.createQuery("SELECT P FROM Post P  WHERE P.id = " +
                ":POST_ID", Post.class);
        jpaQuery.setParameter("POST_ID", 1L);
        Post post1= jpaQuery.getSingleResult();
        System.out.println("TypedQuery JPQL :============== "+ post1);

        //PROJECTION -- list all posts
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaBuilder.createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root); //N+1 problem, as for every post, comment is queried due to toString and as it
        // bi-directional multiple queries are triggered and becomes recursive (stackoverflow exception)
        //alter system set open_cursors = 1000 scope=both;
        Query query = session.createQuery(criteriaQuery);
        List<Post> results = query.getResultList();

        results.forEach(x -> {
            System.out.println(x);
        });

        session.getTransaction().commit();
        session.close();

    }
}
