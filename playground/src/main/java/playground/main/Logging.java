package playground.main;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

public class Logging {
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

        Logger app2 = lc.getLogger("org.hibernate.type.descriptor.sql");
        app2.setAdditive(false);
        app2.setLevel(ch.qos.logback.classic.Level.TRACE);
        app2.addAppender(logConsoleAppender);

        Logger app3 = lc.getLogger("org.hibernate.stat");
        app3.setAdditive(false);
        app3.setLevel(ch.qos.logback.classic.Level.DEBUG);
        app3.addAppender(logConsoleAppender);

        //hibernate.generate_statistics will enable statistics collection and below logger prints it
        Logger app4 = lc.getLogger("org.hibernate.engine.internal");
        app4.setAdditive(false);
        app4.setLevel(ch.qos.logback.classic.Level.INFO);
        app4.addAppender(logConsoleAppender);


        Logger app5 = lc.getLogger("org.springframework.transaction.support");
        app5.setAdditive(false);
        app5.setLevel(ch.qos.logback.classic.Level.TRACE);
        app5.addAppender(logConsoleAppender);

        lc.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME).setLevel(ch.qos.logback.classic.Level.WARN);

    }
}
