package se.king.gamescore.server;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import se.king.gamescore.enums.SessionEnums;
import se.king.gamescore.handler.HighScoreRequestHandler;
import se.king.gamescore.handler.LoginRequestHandler;
import se.king.gamescore.handler.RequestHandler;
import se.king.gamescore.handler.ScoreRequestHandler;
import se.king.gamescore.util.RequestHelper;
import se.king.gamescore.util.RequestURL;
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
            String requestUri = exchange.getRequestURI().toString();

            RequestURL requestURL = RequestHelper.getRequestUrl(requestUri);

            //For the current specification the first element of the path is always some king of id
            String ids = requestURL.getPathList().get(0);

            int id = RequestHelper.isInteger(ids) ? Integer.parseInt(ids) : -1;
            String actualService = requestURL.getPathList().get(1);

            if (id > 0)
            {
                RequestHandler requestHandler = null;

                if (URIEnum.LOGIN.getService().equals(actualService))
                {
                    requestHandler = new LoginRequestHandler(id);
                }
                else if (URIEnum.SCORE.getService().equals(actualService))
                {
                    requestHandler = new ScoreRequestHandler(id,
                        requestURL.getQueryParameters(SessionEnums.SESSION_KEY.getValue()));
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
        }
        catch (NumberFormatException | URISyntaxException e)
        {
            LOG.log(Level.SEVERE, () -> "Technical failure: " + e.getMessage());
        }
        finally
        {
            exchange.getResponseBody().close();
        }
    }
}
