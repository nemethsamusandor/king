package se.king.gameScore.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.handler.HighScoreRequestHandler;

/**
 * Unit test for {@link HighScoreRequestHandlerTest}
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
class HighScoreRequestHandlerTest
{
    @Test
    void handleResponseOK() throws IOException
    {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getResponseBody()).thenReturn(Mockito.mock(OutputStream.class));

        HighScoreRequestHandler scoreRequestHandler = new HighScoreRequestHandler(1);
        scoreRequestHandler.handleResponse(exchange);

        Mockito.verify(exchange, Mockito.times(1)).sendResponseHeaders(
            Mockito.eq(HttpURLConnection.HTTP_OK), Mockito.anyLong());
    }
}