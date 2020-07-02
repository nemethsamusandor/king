package se.king.gamescore.enums;

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
