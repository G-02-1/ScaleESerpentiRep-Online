package Exceptions;

public class CellNotFoundException extends RuntimeException {
    public CellNotFoundException() {};
    public CellNotFoundException(String msg) {
        super(msg);
    }
}
