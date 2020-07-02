package se.king.gamescore.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import se.king.gamescore.enums.URIEnum;

/**
 * Helper class for request processing and validating
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class RequestHelper
{
    // Hide public constructor
    private RequestHelper()
    {
    }

    public static boolean isImplemented(String requestUri) throws URISyntaxException
    {
        RequestURL requestURL = getRequestUrl(requestUri);

        // Name of the service is always on the 2nd place in the path
        if (requestURL.getPathList().size() > 1)
        {
            return Arrays.stream(URIEnum.values())
                .anyMatch(e -> e.getService().equals(requestURL.getPathList().get(1)));
        }
        return false;
    }

    public static RequestURL getRequestUrl(String requestUri) throws URISyntaxException
    {
        URI uri = new URI(requestUri);
        return new RequestURL(uri);
    }

    public static boolean isInteger(String value)
    {
        try
        {
            int number = Integer.parseInt(value);

            return number >= 0;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
}
