package se.king.gamescore.handler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.enums.HttpCodes;
import se.king.gamescore.enums.URIEnum;
import se.king.gamescore.repository.ScoreContext;

/**
 * Handling High Score List request
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class HighScoreRequestHandler implements RequestHandler
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    private final int levelId;

    public HighScoreRequestHandler(int levelId)
    {
        this.levelId = levelId;
    }

    @Override
    public void handleResponse(HttpExchange exchange) throws IOException
    {
        LOG.log(Level.INFO, () -> URIEnum.HIGH_SCORE_LIST.name() + " service called with level id: " + levelId);

        String response = ScoreContext.getInstance().getHighScores(this.levelId);

        exchange.sendResponseHeaders(HttpCodes.OK.getCode(), response.length());
        exchange.getResponseBody().write(response.getBytes());
    }
}
