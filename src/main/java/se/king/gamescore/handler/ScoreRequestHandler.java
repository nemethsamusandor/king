package se.king.gamescore.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.enums.SessionEnums;
import se.king.gamescore.enums.URIEnum;
import se.king.gamescore.repository.ScoreContext;
import se.king.gamescore.session.HttpSession;
import se.king.gamescore.session.SessionContext;
import se.king.gamescore.util.RequestHelper;

/**
 * Handling Score request
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class ScoreRequestHandler implements RequestHandler
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    private final int levelId;
    private final String sessionKey;

    public ScoreRequestHandler(int levelId, String sessionKey)
    {
        this.levelId = levelId;
        this.sessionKey = sessionKey;
    }

    @Override
    public void handleResponse(HttpExchange exchange) throws IOException
    {
        LOG.log(Level.INFO, () -> URIEnum.SCORE.name() + " service called with levelId: " + levelId);

        if (sessionKey != null)
        {
            // Get first value of the sessionKey param
            LOG.log(Level.INFO, () -> URIEnum.SCORE.name() + " service session key: " + sessionKey);

            HttpSession httpSession = SessionContext.getInstance().getHttpSessionBySessionKey(sessionKey);
            int score = getScore(exchange);

            if (httpSession != null && score >= 0)
            {
                int userId = (Integer) httpSession.getAttribute(SessionEnums.USER_ID);

                ScoreContext.getInstance().addScore(levelId, userId, score);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else
            {
                LOG.log(Level.WARNING, () -> URIEnum.SCORE.name() + " - session does not exist or score is not valid!");
                sendBadRequest(exchange);
            }
        }
        else
        {
            LOG.log(Level.WARNING, () -> URIEnum.SCORE.name() + " - sessionKey not set in query parameter!");
            sendBadRequest(exchange);
        }
    }

    private void sendBadRequest(HttpExchange exchange) throws IOException
    {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
    }

    private int getScore(HttpExchange exchange) throws IOException
    {
        try (InputStream is = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is)))
        {
            // Although StringBuilder is not synchronized,
            // it is safe to use here because it is a local variable and only one thread can access it once.
            StringBuilder body = new StringBuilder();

            // Read request body
            String s;
            while ((s = reader.readLine()) != null)
            {
                body.append(s);
            }

            if (RequestHelper.isInteger(body.toString()))
            {
                return Integer.parseInt(body.toString());
            }
            return -1;
        }
    }
}
