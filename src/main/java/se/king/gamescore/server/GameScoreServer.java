package se.king.gamescore.server;

import static se.king.gamescore.config.ApplicationConfig.BACK_LOGGING;
import static se.king.gamescore.config.ApplicationConfig.BASE_URI;
import static se.king.gamescore.config.ApplicationConfig.SERVER_PORT;
import static se.king.gamescore.config.ApplicationConfig.THREAD_POOL_COUNT;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import se.king.gamescore.session.SessionContext;

/**
 * Game score HTTP Server class
 *
 * @author  Sándor Németh
 * @date    30.06.2020
 */
public class GameScoreServer
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");
    private final HttpServer server;

    public GameScoreServer() throws IOException
    {
        // Setup the server server
        server = HttpServer.create(
            new InetSocketAddress(BASE_URI, SERVER_PORT), BACK_LOGGING);
        HttpContext context = server.createContext("/", new GameScoreHttpHandler());

        context.getFilters().add(new BadRequestFilter());

        //Thread pool executor for multi threading
        server.setExecutor(Executors.newFixedThreadPool(THREAD_POOL_COUNT));
    }

    public void start()
    {
        LOG.log(Level.INFO, "Start Game Score server!");

        if (this.server != null)
        {
            // Instantiate the session
            SessionContext.getInstance();

            // Start server server
            server.start();
        }
    }

    public void stop()
    {
        LOG.log(Level.INFO, "Stop Game Score server!");

        if (this.server != null)
        {
            // Stop server server
            server.stop(0);
        }
    }
}
