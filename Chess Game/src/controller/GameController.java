package controller;

import model.GameModel;
import view.GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class GameController {

    private GameModel theModel;
    private GameView theView;

    //game loop components
    public int rotation = 0;
    public int selectedBox[] = new int[2];
    public static final String STARTING_PLAYER_COLOR = "white";

    /**
     * Constructor which connects the Model and the View
     * @param theModel
     * @param theView
     */
    public GameController(GameModel theModel, GameView theView)
    {
        this.theModel = theModel;
        this.theView = theView;
        theView.show();
        theView.addBoxListeners(new ButtonListener());
        theView.addForFeitAndResetListeners(new ForfeitAndNewListener());
        theView.addUndoListeneer(new UndoListener());
        theView.changePlayerPaneColor(STARTING_PLAYER_COLOR);
    }

    /**
     * Inner class to represent Listeners for Forfeit and New
     */
    class ForfeitAndNewListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            char colorForfeited = e.getActionCommand().charAt(0);
            if(colorForfeited=='w')
                theModel.incrementPlayerScore("white");
            else if(colorForfeited=='b')
                theModel.incrementPlayerScore("black");
            else if(colorForfeited=='n')
                theModel.newGame();
            else if(colorForfeited == 'r')
                theModel.resetBoard();
            theView.setWhiteScore(theModel.getPlayerScore("white"));
            theView.setBlackScore(theModel.getPlayerScore("black"));
            theView.resetGame(false);
            theModel.resetBoard();
            theView.changePlayerPaneColor(theModel.getPlayerInTurnColor());

        }
    }

    /**
     * Listens on the undo button only
     */
    class UndoListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            theModel.undoGame();
            theView.undoGame();
            theModel.toggleTurn();
            theView.changePlayerPaneColor(theModel.getPlayerInTurnColor());

        }
    }

    /**
     * Listens to all chess boxes
     */
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //System.out.println(e.getActionCommand());
            int rank = e.getActionCommand().charAt(0) - '0';
            int col = e.getActionCommand().charAt(2)- '0';
            switch(rotation)
            {
                case 0: if(!theModel.isOccupied(rank,col))
                            break;
                        if(theModel.notInTurnBox(rank,col))
                            break;
                        selectBox(rank,col);
                        rotation=1;
                        break;
                case 1: boolean success = moveBoxPiece(rank,col);
                        if(success)
                        {
                            theModel.toggleTurn();
                            theView.changePlayerPaneColor(theModel.getPlayerInTurnColor());
                            theView.moveBox(selectedBox[0],selectedBox[1],rank,col);

//                            System.out.println(theModel.getPlayerInTurnColor() + "s Turn!");
                        }
                        rotation = 0;
                        theView.setBoxesToNormal();
                        int position[] = theModel.kingInCheck();
                        if(position!=null)
                        {
                            theView.setBoxAsInDanger(position[0],position[1]);
                        }
            }


        }
    }

    /**
     * function to try moving a piece
     * Uses the selectedBox to find the coordinates
     * @param rank
     * @param col
     * @return
     */
    private boolean moveBoxPiece(int rank, int col) {
        boolean success = theModel.makeMove(selectedBox[0],selectedBox[1],rank,col);
        return success;
    }


    /**
     * Selects a box both in View and Model
     * also highlights all the possible boxes
     * @param rank
     * @param col
     */
    public void selectBox(int rank, int col) {
        selectedBox[0] = rank;
        selectedBox[1] = col;
        theView.setBoxAsSelected(rank, col);
        ArrayList<int[]> possibleMoves = theModel.getPossibleMoves(rank, col);
        for(int[] box: possibleMoves)
        {
            if(theModel.isOccupied(box[0],box[1]))
            {
                theView.setBoxAsInDanger(box[0],box[1]);
                continue;
            }
            theView.setBoxAsPossible(box[0],box[1]);
        }
    }


}
