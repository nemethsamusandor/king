package se.king.gamescore.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import se.king.gamescore.enums.URIEnum;

public class RequestHelper
{
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

    public static boolean isInteger(String value, int radix) {
        if (value.isEmpty())
        {
            return false;
        }

        for (int i = 0; i < value.length(); i++)
        {
            if (i == 0 && value.charAt(i) == '-')
            {
                if(value.length() == 1)
                    return false;
                else
                    continue;
            }
            if (Character.digit(value.charAt(i), radix) < 0)
                return false;
        }
        return true;
    }
}
