package se.king.gamescore.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.enums.HttpCodes;
import se.king.gamescore.enums.SessionEnums;
import se.king.gamescore.enums.URIEnum;
import se.king.gamescore.repository.ScoreContext;
import se.king.gamescore.session.HttpSession;
import se.king.gamescore.session.SessionContext;

/**
 * Handling Score request
 *
 * @author Sándor Németh
 * @date 01.07.2020
 */
public class ScoreRequestHandler implements RequestHandler
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    private final int levelId;
    private final List<String> sessionKeyParams;

    public ScoreRequestHandler(int levelId, List<String> sessionKeyParams)
    {
        this.levelId = levelId;
        this.sessionKeyParams = sessionKeyParams;
    }

    @Override
    public void handleResponse(HttpExchange exchange) throws IOException
    {
        LOG.log(Level.INFO, () -> URIEnum.SCORE.name() + " service called");

        if (sessionKeyParams != null && !sessionKeyParams.isEmpty())
        {
            // Get first value of the sessionKey param
            String sessionKey = sessionKeyParams.get(0);
            LOG.log(Level.INFO, () -> URIEnum.SCORE.name() + " service session key: " + sessionKey);

            HttpSession httpSession = SessionContext.getInstance().getHttpSessionBySessionKey(sessionKey);

            if (httpSession != null)
            {
                int score = getScore(exchange);
                int userId = (Integer) httpSession.getAttribute(SessionEnums.USER_ID.getValue());

                ScoreContext.getInstance().addScore(levelId, userId, score);

                exchange.sendResponseHeaders(HttpCodes.OK.getCode(), 0);
            }
            else
            {
                sendBadRequest(exchange);
            }
        }
        else
        {
            sendBadRequest(exchange);
        }

    }

    private void sendBadRequest(HttpExchange exchange) throws IOException
    {
        LOG.log(Level.SEVERE, () -> URIEnum.SCORE.name() + " Technical problem, contact Administrator!");

        exchange.sendResponseHeaders(HttpCodes.BAD_REQUEST.getCode(), -1);
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



            return Integer.parseInt(body.toString());
        }
    }
}
