package Exceptions;

public class BoardInstantiationException extends RuntimeException {
    public BoardInstantiationException() {};
    public BoardInstantiationException(String msg) {
        super(msg);
    }
}
