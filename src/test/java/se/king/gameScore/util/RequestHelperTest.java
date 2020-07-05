package se.king.gameScore.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import se.king.gamescore.util.RequestHelper;
import se.king.gamescore.dto.RequestURL;

/**
 * Unit test for RequestHelper
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
class RequestHelperTest
{
    @Test
    void testGetProperRequestURL()
    {
        RequestURL requestURL = RequestHelper.getRequestURL("/12/login");
        //Test login URI
        assertNotNull(requestURL);
        assertEquals("login", requestURL.getService() );
        assertEquals(12, requestURL.getId());

        requestURL = RequestHelper.getRequestURL("/1/score?sessionkey=FJARIL");
        //Test login URI
        assertNotNull(requestURL);
        assertEquals("score", requestURL.getService());
        assertEquals(1,requestURL.getId());
        assertEquals("sessionkey", requestURL.getQueryKey());
        assertEquals("FJARIL", requestURL.getQueryValue());

        requestURL = RequestHelper.getRequestURL("/1/highscorelist");
        //Test login URI
        assertNotNull(requestURL);
        assertEquals("highscorelist", requestURL.getService());
        assertEquals(1, requestURL.getId());
    }

    @Test
    void testGetWrongRequestURL()
    {
        // Too many path parameters
        assertNull(RequestHelper.getRequestURL("/12/login/extra"));

        // Too few path parameters
        assertNull(RequestHelper.getRequestURL("/12"));

        // Query parameter without value
        assertNull(RequestHelper.getRequestURL("/1/score?sessionkey="));

        // Query parameter without key
        assertNull(RequestHelper.getRequestURL("/1/score?=value"));
    }

    @Test
    void testIsInteger()
    {
        // Not a number input
        assertFalse(RequestHelper.isInteger("A"));
        // Input is null
        assertFalse(RequestHelper.isInteger(null));
        // Number is too big, not fit in 31bit unsigned integer
        assertFalse(RequestHelper.isInteger("12345678900"));
        // Number is negative
        assertFalse(RequestHelper.isInteger("-1"));
        // Correct number
        assertTrue(RequestHelper.isInteger("1234"));
    }

    @Test
    void testIsImplemented()
    {
        // Not supported request
        assertFalse(RequestHelper.isImplemented("/1/logging"));
        // Supported request
        assertTrue(RequestHelper.isImplemented("/1/login"));
    }

}