package core;

import java.util.List;

public class Piece {
    String name;
    String color;
    boolean aliveOrNot;
    ChessBox currentPosition;
    Piece killedBy;
    List<Piece> killedPieces;

    public Piece(String name, String color, ChessBox setPosition){
        this.name = name;
        this.color = color;
        setPosition = currentPosition;
        aliveOrNot = true;
    }
    @Override
    public String toString(){
        return String.format(color + " " + name + " at " + currentPosition.toString());
    }
}
