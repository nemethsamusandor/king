package se.king.gamescore.session;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Represents a session object
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class HttpSession
{
    private Map<String, Object> map = new ConcurrentHashMap<>();

    private Date lastVisitTime = new Date();

    synchronized void addAttribute(String name, Object value)
    {
        map.put(name, value);
    }

    public Object getAttribute(String name)
    {
        return map.get(name);
    }

    synchronized Date getLastVisitTime()
    {
        return lastVisitTime;
    }

    synchronized void setLastVisitTime(Date lastVisitTime)
    {
        this.lastVisitTime = lastVisitTime;
    }
}
