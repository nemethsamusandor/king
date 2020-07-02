package se.king.gamescore.session;

import java.util.TimerTask;

public class GameScoreTimerTask extends TimerTask
{
    @Override
    public void run()
    {
        SessionContext.getInstance().removeHttpSessions();
    }
}
