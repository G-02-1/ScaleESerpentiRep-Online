package Exceptions;

public class CardInitializzationException extends RuntimeException{
    public CardInitializzationException() {};
    public CardInitializzationException(String msg) {
        super(msg);
    }
}

