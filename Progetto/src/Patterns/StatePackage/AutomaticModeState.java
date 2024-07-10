package Patterns.StatePackage;

import Exceptions.InvalidStateInstantiationException;
import SimulationObject.Simulation;

public class AutomaticModeState extends State {

    public AutomaticModeState(Object o) {
        if(!(o instanceof Simulation)) {
            throw new InvalidStateInstantiationException("Cannot instantiate an AutomaticModeState for a not Simulation object");
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
        //Do nothing
    }

    @Override
    public void setManualModeState() {
        if(this.o instanceof Simulation) {
            Simulation simulation = (Simulation) o;
            simulation.changeState(new ManualModeState(simulation));
        }
    }

    @Override
    public boolean manual() {
        return true;
    }
}

