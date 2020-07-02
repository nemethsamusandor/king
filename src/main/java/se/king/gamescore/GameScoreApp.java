package se.king.gamescore;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import se.king.gamescore.http.GameScoreServer;

public class GameScoreApp
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    public static void main(String[] args)
    {
        try
        {
            GameScoreServer server = new GameScoreServer();
            server.start();
            LOG.info ("Press q to stop the server.");

            while (true)
            {
                // Just for fun :)
                if (System.in.read() == 113)
                {
                    server.stop();
                    LOG.info("Server stopped!");
                    break;
                }
            }
        }
        catch (IOException e)
        {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }
}
