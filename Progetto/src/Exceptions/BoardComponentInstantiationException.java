package Exceptions;

public class BoardComponentInstantiationException extends RuntimeException {
    public BoardComponentInstantiationException() {};
    public BoardComponentInstantiationException(String msg) {
        super(msg);
    }
}
