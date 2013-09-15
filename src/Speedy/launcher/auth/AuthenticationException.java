package Speedy.launcher.auth;

public class AuthenticationException extends Exception
{

    private static final long serialVersionUID = 0xb0a1a475a1e86b6cL;

    public AuthenticationException()
    {
    }

    public AuthenticationException(String message)
    {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause)
    {
        super(cause);
    }
}
