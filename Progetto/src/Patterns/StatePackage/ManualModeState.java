package Patterns.StatePackage;

import Exceptions.InvalidStateInstantiationException;
import SimulationObject.Simulation;

public class ManualModeState extends State {

    public ManualModeState(Object o) {
        if(!(o instanceof Simulation)) {
            throw new InvalidStateInstantiationException("Cannot instantiate a ManualModeState for a not Simulation object");
        } else {
            this.o = (Simulation) o;
        }
    }

    @Override
    public void setActiveState() {
        //Simulation not a Player, so do nothing
    }

    @Override
    public void setBlockedState(int turns) {
        //Simulation not a Player, so do nothing
    }

    @Override
    public boolean move() {
        //Simulation not a Player, so return false
        return false;
    }

    @Override
    public void setAutomaticModeState() {
        if(this.o instanceof Simulation) {
            Simulation simulation = (Simulation) o;
            simulation.changeState(new AutomaticModeState(simulation));
        }
    }

    @Override
    public void setManualModeState() {
        //Do nothing
    }

    @Override
    public boolean manual() {
        return false;
    }
}
