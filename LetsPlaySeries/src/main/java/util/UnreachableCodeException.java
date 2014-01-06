package util;

public class UnreachableCodeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnreachableCodeException() {
        super("supposedly unreachable code was executed");
    }
}
