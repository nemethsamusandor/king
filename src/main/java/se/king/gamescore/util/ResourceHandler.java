package se.king.gamescore.util;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handling classpath resources
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class ResourceHandler
{
    private static final Logger LOG = Logger.getLogger("gameScoreLogger");

    private static ResourceHandler resourceHandler;
    private Properties properties;

    /**
     * Gets or creates an instance of the ResourceHandler.
     *
     * @return {@link ResourceHandler} instance.
     */
    public static synchronized ResourceHandler getInstance(String resourceFile)
    {
        if (resourceHandler == null)
        {
            resourceHandler = new ResourceHandler(resourceFile);
        }
        return resourceHandler;
    }

    private ResourceHandler(String resourceFile)
    {
        properties = new Properties();
        try
        {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceFile));
        }
        catch (IOException e)
        {
            LOG.log(Level.SEVERE, "Cannot load resource file: {0}", resourceFile);
        }
    }

    public String getProperty(String resourceKey)
    {
        if (properties != null)
        {
            return properties.getProperty(resourceKey);
        }
        return null;
    }
}
