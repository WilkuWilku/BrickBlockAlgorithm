package mainPackage;

/**
 * Created by Inf on 2018-01-09.
 */
public class TimeLimitException extends Exception {
    public TimeLimitException(String message){
        super(message);
    }

    public TimeLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeLimitException(Throwable cause) {
        super(cause);
    }

    public TimeLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
