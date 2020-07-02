package se.king.gamescore.util;

import static java.util.stream.Collectors.toList;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Request URI parser
 *
 * @author Sándor Németh
 * @date 01.07.2020
 */
public class RequestURL
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    private final URI uri;
    private List<String> pathList;
    private Map<String, List<String>> queryElements;

    public RequestURL(URI uri)
    {
        this.uri = uri;
        init();
    }

    public List<String> getPathList()
    {
        return pathList;
    }

    public void setPathList(List<String> pathList)
    {
        this.pathList = pathList;
    }

    public List<String> getQueryParameters(String key)
    {
        if (this.queryElements != null && this.queryElements.keySet().contains(key))
        {
            return this.queryElements.get(key);
        }
        return Collections.emptyList();
    }

    private void init()
    {
        if (uri == null)
        {
            return;
        }

        pathList = splitPath();
        queryElements = splitQuery();
    }

    private List<String> splitPath()
    {
        if (uri.getPath() == null || uri.getPath().isEmpty())
        {
            return Collections.emptyList();
        }

        return Arrays.asList(uri.getPath().replaceAll("^/+(?!$)", "").split("/"));
    }

    private Map<String, List<String>> splitQuery()
    {
        if (uri.getQuery() == null || uri.getQuery().isEmpty())
        {
            return Collections.emptyMap();
        }
        return Arrays.stream(uri.getQuery().split("&"))
            .map(this::splitQueryParameter)
            .collect(Collectors.groupingBy(
                AbstractMap.SimpleImmutableEntry::getKey,
                LinkedHashMap::new,
                Collectors.mapping(Map.Entry::getValue, toList())));
    }

    private AbstractMap.SimpleImmutableEntry<String, String> splitQueryParameter(String it)
    {
        final int idx = it.indexOf('=');
        final String key = idx > 0 ? it.substring(0, idx) : it;
        final String value = idx > 0 && it.length() > idx + 1 ? it.substring(idx + 1) : null;

        try
        {
            return new AbstractMap.SimpleImmutableEntry<>(
                URLDecoder.decode(key, "UTF-8"),
                URLDecoder.decode(value, "UTF-8")
            );
        }
        catch (UnsupportedEncodingException e)
        {
            LOG.severe(String.format("URL decode problem for request uri: %s; Exception: %s",
                uri.getQuery(), e.getMessage()));
        }
        return null;
    }
}
