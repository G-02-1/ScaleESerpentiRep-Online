package Exceptions;

public class NoSuchCellNumberException extends RuntimeException{
    public NoSuchCellNumberException() {};
    public NoSuchCellNumberException(String msg) {
        super(msg);
    }
}