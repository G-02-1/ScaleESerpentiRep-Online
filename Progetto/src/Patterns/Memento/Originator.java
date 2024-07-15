package Patterns.Memento;

import SimulationObject.Simulation;

public interface Originator {
    Memento save();
    Simulation backup(Simulation simulation);

}
