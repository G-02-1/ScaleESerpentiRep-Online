package Exceptions;

public class SpecialCellInitializzationException extends RuntimeException{
    public SpecialCellInitializzationException() {};
    public SpecialCellInitializzationException(String msg) {
        super(msg);
    }
}
