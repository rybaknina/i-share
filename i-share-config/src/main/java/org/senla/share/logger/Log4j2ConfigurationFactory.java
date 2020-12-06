package org.senla.share.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

import java.net.URI;

public class Log4j2ConfigurationFactory extends ConfigurationFactory {

    private Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName("DefaultLogger");
        builder.setStatusLevel(Level.WARN);

        LayoutComponentBuilder standard = builder.newLayout("PatternLayout")
                .addAttribute("pattern", "%d [%t] %-5level: %msg%n%");

        AppenderComponentBuilder console = builder.newAppender("console", "CONSOLE")
                .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        console.add(standard);
        builder.add(console);

        AppenderComponentBuilder file = builder.newAppender("file", "File");
        file.addAttribute("fileName", "../i-share/logging.log");
        file.add(standard);
        builder.add(file);


        ComponentBuilder triggeringPolicy = builder.newComponent("Policies")
                .addComponent(builder.newComponent("CronTriggeringPolicy")
                        .addAttribute("schedule", "0 0 0 * * ?"))
                .addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));
        AppenderComponentBuilder rollingFile = builder.newAppender("rolling", "RollingFile")
                .addAttribute("fileName", "../i-share/log/rolling.log")
                .addAttribute("filePattern", "../i-share/log/archive/rolling-%d{MM-dd-yy}.log.gz")
                .add(standard)
                .addComponent(triggeringPolicy);
        builder.add(rollingFile);

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.WARN);
        rootLogger.add(builder.newAppenderRef("rolling"));
        rootLogger.add(builder.newAppenderRef("console"));
        builder.add(rootLogger);

        return builder.build();
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[]{"*"};
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }
}
