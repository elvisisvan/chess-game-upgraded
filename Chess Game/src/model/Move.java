package model;

import components.ChessBoard;
import components.ChessBox;
import components.piece.Piece;


public class Move {

    ChessBoard chessBoard;
    public Move(ChessBoard chessBoard)
    {
        this.chessBoard = chessBoard;
    }

    /**
     * Used to parse the inputted string from the CONSOLE
     * @param move
     * @return
     */
    public int[] parseMoveString(String move)
    {

        int toReturn[] = new int[4];
        int startColumn = ChessBox.convertColumnCharacter(move.charAt(0));
        int startRow = ChessBox.convertRowCharacter(move.charAt(1));
        int endColumn = ChessBox.convertColumnCharacter(move.charAt(3));
        int endRow = ChessBox.convertRowCharacter(move.charAt(4));
        toReturn[0]=startColumn;
        toReturn[1] = startRow;
        toReturn[2] = endColumn;
        toReturn[3] = endRow;
        return toReturn;
    }

    /**
     * Carries out the movement according to the inputted string from CONSOLE
     * @param moveString
     */
    public void makeMove(String moveString)
    {
        int parsedMove[] = parseMoveString(moveString);

        ChessBox sourceBox = chessBoard.boxes[parsedMove[1]][parsedMove[0]];
        ChessBox destinationBox = chessBoard.boxes[parsedMove[3]][parsedMove[2]];

        //Piece lifted from box
        Piece p =sourceBox.removeCurrentPiece();
//        assert p!=null;

        //only moves the allPieces once its deemed valid
        if(p.checkMoveValidity(chessBoard,destinationBox))
        {
            p.movePiece(destinationBox);
        }
        else {
            //Piece added back to box
            sourceBox.addPiece(p);
            System.out.println("Invalid makeMove!");
        }
    }

    /**
     * Establishes a makeMove of the piece in sourceBox to the destinationBox
     * Only moves if allowed
     * @param sourceBox
     * @param destinationBox
     * @return Returns true is move was successful
     */
    public boolean makeMove(ChessBox sourceBox, ChessBox destinationBox)
    {
        chessBoard.lastDestinationBox = destinationBox;
        chessBoard.lastSourceBox = sourceBox;
        chessBoard.lastSourcePiece = sourceBox.getOccupyingPiece();
        chessBoard.lastDestinationPiece = destinationBox.getOccupyingPiece();

        //Piece lifted from box
        Piece pieceToMove =sourceBox.removeCurrentPiece();

        //only moves the allPieces once its deemed valid
        if(pieceToMove.checkMoveValidity(chessBoard, destinationBox))
        {
            pieceToMove.movePiece(destinationBox);
            return true;
        }
        else {
            //Piece added back to box
            sourceBox.addPiece(pieceToMove);
            System.out.println("Invalid Move!");
            return false;
        }
    }


}
