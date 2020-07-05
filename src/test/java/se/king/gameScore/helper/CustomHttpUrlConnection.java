package se.king.gameScore.helper;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomHttpUrlConnection implements Serializable, AutoCloseable
{
    private final HttpURLConnection connection;

    public CustomHttpUrlConnection(String url, boolean post) throws IOException
    {
        connection = (HttpURLConnection) new URL(url).openConnection();

        if (post)
        {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
        }
    }

    public HttpURLConnection getConnection()
    {
        return connection;
    }

    @Override
    public void close()
    {
        connection.disconnect();
    }
}
