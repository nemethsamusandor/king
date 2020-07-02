package se.king.gamescore;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import se.king.gamescore.server.GameScoreServer;

/**
 * Main app for Game Score server
 *
 * @author  Sándor Németh
 * @date    30.06.2020
 */
public class GameScoreApp
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    public static void main(String[] args)
    {
        try
        {
            GameScoreServer server = new GameScoreServer();
            server.start();
            LOG.log(Level.INFO, "Press q to stop the server.");

            while (true)
            {
                // Just for fun :)
                if (System.in.read() == 113)
                {
                    server.stop();
                    LOG.log(Level.INFO, "Server stopped!");
                    break;
                }
            }
        }
        catch (IOException e)
        {
            LOG.log(Level.SEVERE, () -> "Technical problem: " + e.getMessage());
        }
    }
}
