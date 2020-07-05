package se.king.gamescore.util;

import java.util.Arrays;

import se.king.gamescore.dto.RequestURL;
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

    public static boolean isImplemented(String requestUri)
    {
        RequestURL requestURL = getRequestURL(requestUri);

        // Name of the service is always on the 2nd place in the path
        if (requestURL != null)
        {
            return Arrays.stream(URIEnum.values())
                .anyMatch(e -> e.getService().equals(requestURL.getService()));
        }
        return false;
    }

    public static RequestURL getRequestURL(String requestUri)
    {
        RequestURL requestURL = new RequestURL();

        String[] uriArray = requestUri.split("\\?");

        String queryPart = null;

        if (uriArray.length == 0 || uriArray.length > 2)
        {
            return null;
        }
        else if (uriArray.length == 2)
        {
            queryPart = uriArray[1];
        }

        String[] pathArray = uriArray[0].replaceAll("^/+(?!$)", "").split("/");
        if (pathArray.length != 2)
        {
            return null;
        }
        requestURL.setId(isInteger(pathArray[0]) ? Integer.parseInt(pathArray[0]) : -1);
        requestURL.setService(pathArray[1]);

        if (queryPart != null)
        {
            String[] queryArray = queryPart.split("=");

            if (queryArray.length != 2
                || queryArray[0].isEmpty()
                || queryArray[1].isEmpty())
            {
                return null;
            }
            requestURL.setQueryKey(queryArray[0]);
            requestURL.setQueryValue(queryArray[1]);
        }

        return requestURL;
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
