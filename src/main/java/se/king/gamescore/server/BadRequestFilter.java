package se.king.gamescore.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.util.RequestHelper;

/**
 * Filters out the request what are not available for this server
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class BadRequestFilter extends Filter
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException
    {
        String requestURI = exchange.getRequestURI().toString();
        if (RequestHelper.isImplemented(requestURI))
        {
            chain.doFilter(exchange);
        }
        else
        {
            LOG.log(Level.WARNING, "Not supported request URI: {0}", requestURI);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            exchange.getResponseBody().close();
        }
    }

    @Override
    public String description()
    {
        return "Only given requests can be processed by this server";
    }
}
