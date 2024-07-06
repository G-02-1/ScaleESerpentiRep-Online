package Exceptions;

public class IllegalPositioningException extends RuntimeException{
    public IllegalPositioningException() {};
    public IllegalPositioningException(String msg) {
        super(msg);
    }

}
