package Board.Grid.GridCells;

import Board.Grid.GridBoard.Grid;
import SupportingObjects.Position;
import Exceptions.SpecialCellInitializzationException;
import SupportingObjects.Token;

public class SpecialCell extends Cell {

    private enum Type {
        BENCH, INN, DICES, SPRING, PICKACARD;
    }
    private Type type;

    public SpecialCell(Position position, int number, Grid board, String token) {
        super(position, number, board);
        switch (token) {
            case "BENCH":
                this.type = Type.BENCH;
                break;
            case "INN":
                this.type = Type.INN;
                break;
            case "DICES":
                this.type = Type.DICES;
                break;
            case "SPRING":
                this.type = Type.SPRING;
                break;
            case "PICKACARD":
                this.type = Type.PICKACARD;
                break;
            default:
                throw new SpecialCellInitializzationException("Invalid token: " + token);
        }
    }

    private boolean isSTOPOVER() {
        return this.type.equals(Type.BENCH) || this.type.equals(Type.INN);
    }

    private boolean isBENCH() {
        return this.type.equals(Type.BENCH);
    }

    private boolean isINN() {
        return this.type.equals(Type.INN);
    }

    private boolean isPRIZE() {
        return this.type.equals(Type.DICES) || this.type.equals(Type.SPRING);
    }

    private boolean isDICES() {
        return this.type.equals(Type.DICES);
    }

    private boolean isSPRING() {
        return this.type.equals(Type.SPRING);
    }

    private boolean isPICKACARD() {
        return this.type.equals(Type.PICKACARD);
    }

    @Override
    public int triggerEffect() {
        if(this.isPRIZE()) {
            if(this.isDICES()) {
                return 2;
            }
            if(this.isSPRING()) {
                return 3;
            }
        }
        if(this.isSTOPOVER()) {
            if(this.isBENCH()) {
                return 4;
            }
            if(this.isINN()) {
                return 5;
            }
        }
        if(this.isPICKACARD()) {
            return 6;
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.type.name();
    }
}
