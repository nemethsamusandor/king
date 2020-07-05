package se.king.gamescore.config;

/**
 * GameScore app configurations
 * Values should be mapped from resources in live environment and only default should be set here
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class Configs
{
    private Configs()
    {
    }

    public static final String BASE_URI = "localhost";
    public static final int SERVER_PORT = 8329;
    public static final int BACK_LOGGING = 0;
    // THREAD_POOL_COUNT is counted using formula: Number of threads = Number of Available Cores * (1 + Wait time / Service time)
    public static final int THREAD_POOL_COUNT = Runtime.getRuntime().availableProcessors() * 20;

    // SESSION_TIMEOUT timeout of the session in milliseconds
    public static final int SESSION_TIMEOUT = 10 * 60 * 1000;
    // SESSION_TIMER_DELAY delay in milliseconds before task is to be executed
    public static final int SESSION_TIMER_DELAY = 0 * 60 * 1000;
    // SESSION_TIMER_DELAY time in milliseconds between successive task executions.
    public static final int SESSION_TIMER_PERIOD = 1 * 60 * 1000;

    public static final int HIGH_SCORE_LIMIT = 15;
}
