package se.king.gamescore.config;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger configuration
 * using this configuration configure VM options
 * -Djava.util.logging.config.class=se.king.gamescore.config.LoggingConfig
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class LoggingConfig
{
    public LoggingConfig()
    {
        try
        {
            // Programmatic configuration
            System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL %4$-7s [%3$s] (%2$s) %5$s %6$s%n");

            final ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.ALL);
            consoleHandler.setFormatter(new SimpleFormatter());

            final Logger app = Logger.getLogger("gameScoreLogger");
            app.setLevel(Level.ALL);
            app.addHandler(consoleHandler);
        }
        catch (Exception e)
        {
            // The runtime won't show stack traces if the exception is thrown
            e.printStackTrace();
        }
    }
}
