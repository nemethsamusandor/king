package se.king.gameScore.handler;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import se.king.gamescore.handler.LoginRequestHandler;

/**
 * Unit test for {@link LoginRequestHandlerTest}
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
class LoginRequestHandlerTest
{
    @Test
    void handleResponse() throws IOException
    {
        HttpExchange exchange = Mockito.mock(HttpExchange.class);
        Mockito.when(exchange.getResponseBody()).thenReturn(Mockito.mock(OutputStream.class));

        LoginRequestHandler loginRequestHandler = new LoginRequestHandler(1);

        loginRequestHandler.handleResponse(exchange);

        Mockito.verify(exchange,
            Mockito.times(1)).sendResponseHeaders(Mockito.anyInt(), Mockito.anyLong());

        Mockito.verify(exchange,
            Mockito.times(1)).getResponseBody();
    }
}