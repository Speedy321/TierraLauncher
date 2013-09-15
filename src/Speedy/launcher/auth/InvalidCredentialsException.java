package Speedy.launcher.auth;

public class InvalidCredentialsException extends AuthenticationException
{

    private static final long serialVersionUID = 0xc581de066ac8d710L;

    public InvalidCredentialsException()
    {
    }

    public InvalidCredentialsException(String message)
    {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public InvalidCredentialsException(Throwable cause)
    {
        super(cause);
    }
}
