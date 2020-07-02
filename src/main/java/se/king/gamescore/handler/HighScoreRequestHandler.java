package se.king.gamescore.handler;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.repository.ScoreContext;

/**
 * Handling High Score List request
 *
 * @author Sándor Németh
 * @date  01.07.2020
 */
public class HighScoreRequestHandler implements RequestHandler
{
    private final int levelId;

    public HighScoreRequestHandler(int levelId)
    {
        this.levelId = levelId;
    }

    @Override
    public void handleResponse(HttpExchange exchange) throws IOException
    {
        String response = ScoreContext.getInstance().getHighScores(this.levelId);

        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
    }
}
