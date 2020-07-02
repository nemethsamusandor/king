package se.king.gamescore.enums;

/**
 * Session constants
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public enum SessionEnums
{
    SESSION_KEY("sessionkey"),
    USER_ID("userid");

    private final String value;

    SessionEnums(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
