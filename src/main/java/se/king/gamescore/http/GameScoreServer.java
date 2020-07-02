package se.king.gamescore.http;

import static se.king.gamescore.config.Configs.BACK_LOGGING;
import static se.king.gamescore.config.Configs.BASE_URI;
import static se.king.gamescore.config.Configs.SERVER_PORT;
import static se.king.gamescore.config.Configs.THREAD_POOL_COUNT;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import se.king.gamescore.session.SessionContext;

/**
 * Game score HTTP Server class
 *
 * @author Sándor Németh
 * @date 30.06.2020
 */
public class GameScoreServer
{
    private final HttpServer server;

    public GameScoreServer() throws IOException
    {
        // Setup the http
        server = HttpServer.create(new InetSocketAddress(BASE_URI.getValue(), SERVER_PORT.getIntValue()), BACK_LOGGING.getIntValue());
        HttpContext context = server.createContext("/", new GameScoreHttpHandler());

        context.getFilters().add(new BadRequestFilter());

        //Thread pool executor for multi threading
        server.setExecutor(Executors.newFixedThreadPool(THREAD_POOL_COUNT.getIntValue()));
    }

    public void start()
    {
        if (this.server != null)
        {
            // Instantiate the session
            SessionContext.getInstance();

            // Start web server
            server.start();
        }
    }

    public void stop()
    {
        if (this.server != null)
        {
            server.stop(0);
        }
    }
}
