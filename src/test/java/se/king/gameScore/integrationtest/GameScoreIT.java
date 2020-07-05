package se.king.gameScore.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import se.king.gameScore.helper.CustomHttpUrlConnection;
import se.king.gameScore.helper.dto.HttpResponse;
import se.king.gamescore.server.GameScoreServer;

/**
 * Integration test for GameScore server
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GameScoreIT
{
    private final static String LOGIN_URL = "http://localhost:8329/%s/login";
    private final static String SCORE_URL = "http://localhost:8329/%s/score?sessionkey=%s";
    private final static String HIGHSCORELIST_URL = "http://localhost:8329/%s/highscorelist";


    private GameScoreServer server = null;

    @BeforeAll
    private void setup()
    {
        try
        {
            server = new GameScoreServer();
            server.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @AfterAll
    private void destroy()
    {
        server.stop();
    }

    @Test
    void testLogin()
    {
        // Test login request
        HttpResponse response = sendGET(String.format(LOGIN_URL, 1));
        assertEquals(HttpURLConnection.HTTP_OK, response.getResponseCode());
    }

    @Test
    void testScore()
    {
        // Test score request

        // Login first user
        HttpResponse loginResponse1 = sendGET(String.format(LOGIN_URL, 1));

        // Add score to user 1 on level 10
        assertEquals(HttpURLConnection.HTTP_OK,
            sendPOST(String.format(SCORE_URL, 10, loginResponse1.getResponseMessage()), "2100").getResponseCode());
        // Add score to user 1 on level 9
        assertEquals(HttpURLConnection.HTTP_OK,
            sendPOST(String.format(SCORE_URL, 9, loginResponse1.getResponseMessage()), "1200").getResponseCode());

        // Login first user
        HttpResponse loginResponse2 = sendGET(String.format(LOGIN_URL, 2));
        // Add score to user 2 on level 10
        assertEquals(HttpURLConnection.HTTP_OK,
            sendPOST(String.format(SCORE_URL, 10, loginResponse2.getResponseMessage()), "2200").getResponseCode());
    }

    @Test
    void testHighScore()
    {
        // Test high score list request on level 10
        HttpResponse response1 = sendGET(String.format(HIGHSCORELIST_URL, 10));
        assertEquals(HttpURLConnection.HTTP_OK, response1.getResponseCode());
        assertEquals("2200=2,2100=1", response1.getResponseMessage());

        // Test high score list request on level 9
        HttpResponse response2 = sendGET(String.format(HIGHSCORELIST_URL, 9));
        assertEquals(HttpURLConnection.HTTP_OK, response2.getResponseCode());
        assertEquals("1200=1", response2.getResponseMessage());

    }

    private HttpResponse sendGET(String url)
    {
        HttpResponse response = new HttpResponse();
        // Test login
        try (CustomHttpUrlConnection conn = new CustomHttpUrlConnection(url, false);
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getConnection().getInputStream())))
        {
            int responseCode = conn.getConnection().getResponseCode();
            response.setResponseCode(responseCode);
            System.out.println("GET Response Code: " + responseCode);

            String line;
            StringBuilder responseMessage = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                responseMessage.append(line);
            }

            response.setResponseMessage(responseMessage.toString());
            System.out.println("GET Response body: " + responseMessage.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return response;
    }

    private HttpResponse sendPOST(String url, String requestBody)
    {
        HttpResponse response = new HttpResponse();

        try (CustomHttpUrlConnection conn = new CustomHttpUrlConnection(url, true);
            OutputStream os = conn.getConnection().getOutputStream())
        {
            os.write(requestBody.getBytes());
            os.flush();

            int responseCode = conn.getConnection().getResponseCode();
            System.out.println("POST Response Code: " + responseCode);
            response.setResponseCode(responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                response.setResponseMessage(conn.getConnection().getResponseMessage());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return response;
    }
}
