package se.king.gamescore.enums;

/**
 * Available request types
 *
 * @author  Sándor Németh
 * @date    01.07.2020
 */
public enum URIEnum
{
    LOGIN("login"),
    SCORE("score"),
    HIGH_SCORE_LIST("highscorelist");

    private final String service;

    URIEnum(String service)
    {
        this.service = service;
    }

    public String getService()
    {
        return service;
    }
}
