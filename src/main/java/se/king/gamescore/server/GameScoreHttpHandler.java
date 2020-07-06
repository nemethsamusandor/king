package se.king.gamescore.server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import se.king.gamescore.enums.SessionConstants;
import se.king.gamescore.handler.HighScoreRequestHandler;
import se.king.gamescore.handler.LoginRequestHandler;
import se.king.gamescore.handler.RequestHandler;
import se.king.gamescore.handler.ScoreRequestHandler;
import se.king.gamescore.util.RequestHelper;
import se.king.gamescore.dto.RequestURL;
import se.king.gamescore.enums.URIEnum;

/**
 * HTTP handler class for distribute request for proper handling
 *
 * @author  Sándor Németh
 * @date    30.06.2020
 */
public class GameScoreHttpHandler implements HttpHandler
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
        try
        {
            RequestURL requestURL = RequestHelper.getRequestURL(exchange.getRequestURI().toString());

            if (requestURL != null)
            {
                int id = requestURL.getId();
                String actualService = requestURL.getService();

                if (id >= 0)
                {
                    RequestHandler requestHandler = null;

                    if (URIEnum.LOGIN.getService().equals(actualService))
                    {
                        requestHandler = new LoginRequestHandler(id);
                    }
                    else if (URIEnum.SCORE.getService().equals(actualService)
                        && SessionConstants.SESSION_KEY.equals(requestURL.getQueryKey()))
                    {
                        requestHandler = new ScoreRequestHandler(id, requestURL.getQueryValue());
                    }
                    else if (URIEnum.HIGH_SCORE_LIST.getService().equals(actualService))
                    {
                        requestHandler = new HighScoreRequestHandler(id);
                    }

                    if (requestHandler != null)
                    {
                        requestHandler.handleResponse(exchange);
                    }
                }
                else
                {
                    LOG.log(Level.WARNING, "id is not valid!");
                }
            }
            else
            {
                LOG.log(Level.WARNING, "Request is not valid!");
            }
        }
        catch (NumberFormatException e)
        {
            LOG.log(Level.SEVERE, () -> "Technical failure: " + e.getMessage());
        }
        finally
        {
            exchange.getResponseBody().close();
        }
    }
}
