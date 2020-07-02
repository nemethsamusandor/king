package se.king.gamescore.enums;

public enum HttpCodes
{
    OK(200),
    BAD_REQUEST(400);

    private final int code;

    HttpCodes(int code)
    {
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }
}
