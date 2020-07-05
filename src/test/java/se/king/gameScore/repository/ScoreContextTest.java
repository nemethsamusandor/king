package se.king.gameScore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import se.king.gamescore.session.SessionContext;

/**
 * Unit test for ScoreContext
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
class ScoreContextTest
{
    @Test
    void testGetInstance()
    {
        //Test immutability
        assertEquals(SessionContext.getInstance(), SessionContext.getInstance());
    }

    @Test
    void testScoreContext()
    {

    }

    @Test
    void getHighScores()
    {
    }
}