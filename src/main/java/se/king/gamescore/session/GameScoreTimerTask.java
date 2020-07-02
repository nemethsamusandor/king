package se.king.gamescore.session;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Timer task used to schedule session timeouts
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class GameScoreTimerTask extends TimerTask
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    @Override
    public void run()
    {
        LOG.log(Level.FINEST, () -> "Removing expired session elements");
        SessionContext.getInstance().removeHttpSessions();
    }
}
