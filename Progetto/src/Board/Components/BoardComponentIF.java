package Board.Components;

import SupportingObjects.Position;

import java.io.Serializable;
import java.util.LinkedList;

public interface BoardComponentIF extends Serializable {

    /**
     *
     * @return true if is a Ladder board component.
     */
    boolean isLadder();

    /**
     *
     * @return true if is a Snake board component.
     */
    boolean isSnake();

    /**
     *
     * @return the position that trigger object's effect.
     */
    Position getActivePosition();

    /**
     *
     * @return the position to which the player was transported.
     */
    Position getPassivePosition();

    /**
     *
     * @return cells over which the object extends.
     */
    LinkedList<Position> getExtension();

}
