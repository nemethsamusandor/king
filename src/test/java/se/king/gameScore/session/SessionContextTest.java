package se.king.gameScore.session;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import se.king.gamescore.enums.SessionConstants;
import se.king.gamescore.session.HttpSession;
import se.king.gamescore.session.SessionContext;

/**
 * Unit test for SessionContext class
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
class SessionContextTest
{
    @Test
    void getInstance()
    {
        //Test immutability
        assertEquals(SessionContext.getInstance(), SessionContext.getInstance());
    }

    @Test
    void getHttpSessionByUserId()
    {
        // User 1 session
        HttpSession session1 = SessionContext.getInstance().getHttpSessionByUserId(1);

        assertNotNull(session1.getAttribute(SessionConstants.SESSION_KEY));
        assertNotEquals("", session1.getAttribute(SessionConstants.SESSION_KEY));
        assertEquals(1, session1.getAttribute(SessionConstants.USER_ID));

        // User 2 session
        HttpSession session2 = SessionContext.getInstance().getHttpSessionByUserId(2);

        assertNotEquals(session1.getAttribute(SessionConstants.SESSION_KEY),
            session2.getAttribute(SessionConstants.SESSION_KEY));
    }

    @Test
    void getHttpSessionBySessionKey()
    {
        // User session
        HttpSession session = SessionContext.getInstance().getHttpSessionByUserId(1);
        HttpSession session1 = SessionContext.getInstance().getHttpSessionByUserId(2);

        HttpSession session2 = SessionContext.getInstance().getHttpSessionBySessionKey(
            (String) session.getAttribute(SessionConstants.SESSION_KEY));

        HttpSession session3 = SessionContext.getInstance().getHttpSessionBySessionKey("NOT_VALID_SESSION");

        assertEquals(session, session2);
        assertNotEquals(session, session1);
        assertNull(session3);
    }

    @Test
    void removeHttpSessions()
    {
        HttpSession session = SessionContext.getInstance().getHttpSessionByUserId(10);
        String sessionKey = (String) session.getAttribute(SessionConstants.SESSION_KEY);

        // Remove invalid sessions
        SessionContext.getInstance().removeHttpSessions();

        // Get session, what should still be active
        assertNotNull(SessionContext.getInstance().getHttpSessionBySessionKey(sessionKey));

        // Set back session time with 20 minutes
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(session.getLastVisitTime());
        calendar.add(Calendar.MINUTE, -20);

        session.setLastVisitTime(calendar.getTime());

        // Remove invalid sessions
        SessionContext.getInstance().removeHttpSessions();

        // Session should be removed
        assertNull(SessionContext.getInstance().getHttpSessionBySessionKey(sessionKey));
    }
}