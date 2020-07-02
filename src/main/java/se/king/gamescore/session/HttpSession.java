package se.king.gamescore.session;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a session object
 *
 * @author Sándor Németh
 * @date 01.07.2020
 */
public class HttpSession
{
    private Map<String, Object> map = new HashMap<>();

    private Date lastVisitTime = new Date();

    public void addAttribute(String name, Object value)
    {
        map.put(name, value);
    }

    public Object getAttribute(String name)
    {
        return map.get(name);
    }

    public Map<String, Object> getAllAttribute()
    {
        return map;
    }

    public Set<String> getAllNames()
    {
        return map.keySet();
    }

    public boolean containsName(String name)
    {
        return map.containsKey(name);
    }

    public Map<String, Object> getMap()
    {
        return map;
    }

    public void setMap(Map<String, Object> map)
    {
        this.map = map;
    }

    public Date getLastVisitTime()
    {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime)
    {
        this.lastVisitTime = lastVisitTime;
    }
}
