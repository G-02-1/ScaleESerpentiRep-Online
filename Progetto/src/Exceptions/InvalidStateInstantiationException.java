package Exceptions;

public class InvalidStateInstantiationException extends RuntimeException {
    public InvalidStateInstantiationException() {};
    public InvalidStateInstantiationException(String msg) {
        super(msg);
    }
}
