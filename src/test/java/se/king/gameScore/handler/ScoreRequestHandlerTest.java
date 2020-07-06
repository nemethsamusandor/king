package se.king.gameScore.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.sun.net.httpserver.HttpExchange;

import se.king.gamescore.handler.ScoreRequestHandler;

/**
 * Unit test for {@link ScoreRequestHandlerTest}
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
class ScoreRequestHandlerTest
{
    @Test
    void handleResponseOK() throws IOException
    {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getRequestBody()).thenReturn(
            new ByteArrayInputStream("FJARIL".getBytes()));

        ScoreRequestHandler scoreRequestHandler = new ScoreRequestHandler(1, "FJARIL");
        scoreRequestHandler.handleResponse(exchange);

        Mockito.verify(exchange, Mockito.times(1)).sendResponseHeaders(
            Mockito.eq(HttpURLConnection.HTTP_OK), Mockito.anyLong());
    }

    @Test
    void handleResponseNotOK() throws IOException
    {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);

        ScoreRequestHandler scoreRequestHandler = new ScoreRequestHandler(1, null);
        scoreRequestHandler.handleResponse(exchange);

        Mockito.verify(exchange,
            Mockito.times(1)).sendResponseHeaders(
                Mockito.eq(HttpURLConnection.HTTP_BAD_REQUEST), Mockito.anyLong());
    }

}