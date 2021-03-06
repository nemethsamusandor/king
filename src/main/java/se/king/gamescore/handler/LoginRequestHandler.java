package se.king.gamescore.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.enums.SessionConstants;
import se.king.gamescore.session.SessionContext;
import se.king.gamescore.enums.URIEnum;

/**
 * Handling Login request
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class LoginRequestHandler implements RequestHandler
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    private final int userId;

    public LoginRequestHandler(int userId)
    {
        this.userId = userId;
    }

    @Override
    public void handleResponse(HttpExchange exchange) throws IOException
    {
        LOG.log(Level.INFO, () -> URIEnum.LOGIN.name() + " service called with user id: " + userId);

        String sessionKey = (String) SessionContext.getInstance()
            .getHttpSessionByUserId(userId).getAttribute(SessionConstants.SESSION_KEY);

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, sessionKey.length());
        exchange.getResponseBody().write(sessionKey.getBytes());
    }
}
