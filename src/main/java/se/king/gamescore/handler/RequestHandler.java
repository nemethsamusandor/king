package se.king.gamescore.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

/**
 * Interface for handling application requests
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public interface RequestHandler
{
    void handleResponse(HttpExchange exchange) throws IOException;
}
