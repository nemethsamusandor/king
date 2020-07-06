package se.king.gamescore.config;

import se.king.gamescore.util.ResourceHandler;

/**
 * GameScore application configurations handling
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class ApplicationConfig
{
    private static ResourceHandler resourceHandler;

    private ApplicationConfig()
    {
    }

    public static final String BASE_URI = getPropertyString("base_uri", "localhost");
    public static final int SERVER_PORT = getPropertyInt("server_port", 8329);
    public static final int BACK_LOGGING = getPropertyInt("back_logging", 0);
    // THREAD_POOL_COUNT is counted using formula: Number of threads = Number of Available Cores * (1 + Wait time / Service time)
    public static final int THREAD_POOL_COUNT = Runtime.getRuntime().availableProcessors() * 20;

    // SESSION_TIMEOUT timeout of the session in milliseconds
    public static final int SESSION_TIMEOUT = getPropertyInt("session_timeout", 10) * 60 * 1000;
    // SESSION_TIMER_DELAY delay in milliseconds before task is to be executed
    public static final int SESSION_TIMER_DELAY = getPropertyInt("session_timer_delay", 0) * 60 * 1000;
    // SESSION_TIMER_DELAY time in milliseconds between successive task executions.
    public static final int SESSION_TIMER_PERIOD = getPropertyInt("session_timer_period", 1) * 60 * 1000;

    public static final int HIGH_SCORE_LIMIT = getPropertyInt("high_score_limit", 15);

    private static int getPropertyInt(String resourceKey, int defaultValue)
    {
        return getProperty(resourceKey) == null ? defaultValue : Integer.parseInt(getProperty(resourceKey));
    }

    private static String getPropertyString(String resourceKey, String defaultValue)
    {
        return getProperty(resourceKey) == null ? defaultValue : getProperty(resourceKey);
    }

    private static String getProperty(String resourceKey)
    {
        if (resourceHandler == null)
        {
            resourceHandler = ResourceHandler.getInstance("application.properties");
        }

        return resourceHandler.getProperty(resourceKey);
    }
}
