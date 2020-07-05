package se.king.gamescore.dto;

/**
 * Request URI parser
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public class RequestURL
{
    private int id;
    private String service;
    private String queryKey;
    private String queryValue;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getService()
    {
        return service;
    }

    public void setService(String service)
    {
        this.service = service;
    }

    public String getQueryKey()
    {
        return queryKey;
    }

    public void setQueryKey(String queryKey)
    {
        this.queryKey = queryKey;
    }

    public String getQueryValue()
    {
        return queryValue;
    }

    public void setQueryValue(String queryValue)
    {
        this.queryValue = queryValue;
    }
}
