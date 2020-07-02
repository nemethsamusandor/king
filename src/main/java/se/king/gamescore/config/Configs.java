package se.king.gamescore.config;

/**
 * GameScore app configurations
 * This should be replaced in live environment with resources
 *
 * @author Sándor Németh
 *
 * @date 01.07.2020
 */
public enum Configs
{
    BASE_URI("localhost"),
    SERVER_PORT("8329"),
    BACK_LOGGING("0"),
    // THREAD_POOL_COUNT is counted using formula: Number of threads = Number of Available Cores * (1 + Wait time / Service time)
    THREAD_POOL_COUNT(String.valueOf(Runtime.getRuntime().availableProcessors() * 20)),

    // SESSION_TIMEOUT timeout of the session in milliseconds
    SESSION_TIMEOUT(String.valueOf(10 * 60 * 1000)),
    // SESSION_TIMER_DELAY delay in milliseconds before task is to be executed
    SESSION_TIMER_DELAY(String.valueOf(0 * 60 * 1000)),
    // SESSION_TIMER_DELAY time in milliseconds between successive task executions.
    SESSION_TIMER_PERIOD(String.valueOf(1 * 60 * 1000)),

    HIGH_SCORE_LIMIT(String.valueOf(15));

    private final String value;

    Configs(final String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public Integer getIntValue()
    {
        return Integer.valueOf(value);
    }

    public Long getLongValue()
    {
        return Long.valueOf(value);
    }
}
