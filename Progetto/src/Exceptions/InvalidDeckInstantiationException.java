package Exceptions;

public class InvalidDeckInstantiationException extends RuntimeException {
    public InvalidDeckInstantiationException() {};
    public InvalidDeckInstantiationException(String msg) {
        super(msg);
    }

}
