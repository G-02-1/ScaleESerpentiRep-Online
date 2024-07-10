package Exceptions;

public class IllegalSimulationInitializzation extends RuntimeException {
    public IllegalSimulationInitializzation() {};
    public IllegalSimulationInitializzation(String msg) {
        super(msg);
    }
}
