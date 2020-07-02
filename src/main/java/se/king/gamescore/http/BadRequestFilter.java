package se.king.gamescore.http;

import java.io.IOException;
import java.net.URISyntaxException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.config.Configs;
import se.king.gamescore.enums.HttpCodes;
import se.king.gamescore.util.RequestHelper;

public class BadRequestFilter extends Filter
{
    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException
    {
        try
        {
            if (RequestHelper.isImplemented(exchange.getRequestURI().toString()))
            {
                chain.doFilter(exchange);
            }
            else
            {
                exchange.sendResponseHeaders(HttpCodes.BAD_REQUEST.getCode(), -1);
                exchange.getResponseBody().close();
            }
        }
        catch (URISyntaxException e)
        {
            throw new IOException(e);
        }
    }

    @Override
    public String description()
    {
        return "Filter the bad requests";
    }
}
