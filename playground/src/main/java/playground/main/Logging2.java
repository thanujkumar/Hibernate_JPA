package playground.main;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

public class Logging2 {
    static {
        System.setProperty("hibernate.generate_statistics", "true");
        System.setProperty("hibernate.format_sql", "true");

        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        PatternLayoutEncoder ple = new PatternLayoutEncoder();
        ple.setPattern("%date %level [%thread] %logger{10} [%file:%line] %msg%n");
        ple.setContext(lc);
        ple.start();

        ConsoleAppender logConsoleAppender = new ConsoleAppender();
        logConsoleAppender.setContext(lc);
        logConsoleAppender.setName("console");
        logConsoleAppender.setEncoder(ple);
        logConsoleAppender.start();

        Logger app1 = lc.getLogger("org.hibernate.SQL");
        app1.setAdditive(false);
        app1.setLevel(ch.qos.logback.classic.Level.DEBUG);
        app1.addAppender(logConsoleAppender);

        //Query bind parameters
//        Logger app2 = lc.getLogger("org.hibernate.type");
//        app2.setAdditive(false);
//        app2.setLevel(ch.qos.logback.classic.Level.TRACE);
//        app2.addAppender(logConsoleAppender);

        //Result bind parameter
//        Logger app3 = lc.getLogger("org.hibernate.type.descriptor.sql");
//        app3.setAdditive(false);
//        app3.setLevel(ch.qos.logback.classic.Level.TRACE);
//        app3.addAppender(logConsoleAppender);

        Logger app4 = lc.getLogger("org.hibernate.stat");
        app4.setAdditive(false);
        app4.setLevel(ch.qos.logback.classic.Level.DEBUG);
        app4.addAppender(logConsoleAppender);

        //hibernate.generate_statistics will enable statistics collection and below logger prints it
        Logger app5 = lc.getLogger("org.hibernate.engine.internal");
        app5.setAdditive(false);
        app5.setLevel(ch.qos.logback.classic.Level.INFO);
        app5.addAppender(logConsoleAppender);


        Logger app6 = lc.getLogger("org.springframework.transaction.support");
        app6.setAdditive(false);
        app6.setLevel(ch.qos.logback.classic.Level.INFO);//TRACE
        app6.addAppender(logConsoleAppender);

        Logger app7 = lc.getLogger("com.arjuna");
        app7.setAdditive(false);
        app7.setLevel(ch.qos.logback.classic.Level.INFO);//TRACE
        app7.addAppender(logConsoleAppender);

        Logger app8 = lc.getLogger("oracle.ucp");
        app8.setAdditive(false);
        app8.setLevel(ch.qos.logback.classic.Level.TRACE);
        app8.addAppender(logConsoleAppender);

        Logger app9 = lc.getLogger("org.springframework");
        app9.setAdditive(false);
        app9.setLevel(ch.qos.logback.classic.Level.INFO);//TRACE
        app9.addAppender(logConsoleAppender);

        Logger app10 = lc.getLogger("org.hibernate.resource.transaction.backend.jta");
        app10.setAdditive(false);
        app10.setLevel(ch.qos.logback.classic.Level.TRACE);
        app10.addAppender(logConsoleAppender);


        lc.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).setLevel(ch.qos.logback.classic.Level.WARN);

    }
}
