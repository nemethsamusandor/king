package se.king.gamescore.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

/**
 * Interface for handling different requests
 */
public interface RequestHandler
{
    void handleResponse(HttpExchange exchange) throws IOException;
}
