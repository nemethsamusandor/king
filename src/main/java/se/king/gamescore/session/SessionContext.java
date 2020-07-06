package se.king.gamescore.session;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import se.king.gamescore.config.ApplicationConfig;
import se.king.gamescore.enums.SessionEnums;

/**
 * Create and manage sessions
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class SessionContext
{
    private static SessionContext instance;
    private Map<Integer, HttpSession> sessionMap;

    /**
     * Constructor is private to hide the public one
     * because this class is a Singleton
     */
    private SessionContext()
    {
        this.sessionMap = new ConcurrentHashMap<>();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new GameScoreTimerTask(), ApplicationConfig.SESSION_TIMER_DELAY,
            ApplicationConfig.SESSION_TIMER_PERIOD);
    }

    /**
     * Gets or creates an instance of the SessionContext.
     *
     * @return {@link SessionContext} instance.
     */
    public static synchronized SessionContext getInstance()
    {
        if (instance == null)
        {
            instance = new SessionContext();
        }
        return instance;
    }

    /**
     * Make a unique session key
     * @return upper case session key
     */
    private String makeSessionKey()
    {
        return new BigInteger(32, new SecureRandom()).toString(32).toUpperCase();
    }

    /**
     * Create or update a HttpSession
     * @param userId identify the session - 31 bit unsigned int
     * @return {@link HttpSession} HttpSession object
     */
    public HttpSession getHttpSessionByUserId(int userId)
    {
        HttpSession httpSession = sessionMap.get(userId);

        if (httpSession != null)
        {
            httpSession.setLastVisitTime(new Date());
            sessionMap.replace(userId, httpSession);
        }
        else
        {
            httpSession = new HttpSession();
            httpSession.addAttribute(SessionEnums.SESSION_KEY, makeSessionKey());
            httpSession.addAttribute(SessionEnums.USER_ID, userId);
            sessionMap.putIfAbsent(userId, httpSession);
        }

        return httpSession;
    }

    /**
     * Get a HttpSession by sessionKey
     * @param sessionKey sessionKey
     * @return {@link HttpSession} HttpSession object
     */
    public HttpSession getHttpSessionBySessionKey(String sessionKey)
    {
        HttpSession httpSession = sessionMap.entrySet().stream()
            .filter(h -> h.getValue().getAttribute(SessionEnums.SESSION_KEY).equals(sessionKey))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(null);

        if (httpSession != null)
        {
            // Keep session live
            int userId = (Integer) httpSession.getAttribute(SessionEnums.USER_ID);
            httpSession.setLastVisitTime(new Date());
            sessionMap.replace(userId, httpSession);
        }

        return httpSession;
    }

    /**
     * Removes the httpSessions that are expired
     */
    public void removeHttpSessions()
    {
        final Date now = new Date();

        sessionMap.forEach((key, httpSession) -> {
            if (now.getTime() - httpSession.getLastVisitTime().getTime() > ApplicationConfig.SESSION_TIMEOUT)
            {
                sessionMap.remove(key);
            }
        });
    }
}
