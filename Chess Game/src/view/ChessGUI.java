package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;


public class ChessGUI{

    public JFrame gameContainer = new JFrame("Chess Game");
    public JPanel chessBoard;
    public JPanel gameScreen;
    private JButton[][] chessBoxes;

    //toolbar components
    private JPanel toolBar;
    private JButton newButton;
    private JButton undoButton;

    //player-pane components
    private JSplitPane playerPanes;
    private JPanel playerWhite;
    private JPanel playerBlack;
    private JLabel playerBlackName;
    private JLabel playerWhiteName;
    private JLabel playerBlackImage;
    private JLabel playerWhiteImage;
    private JButton playerBlackForfeitButton;
    private JButton playerWhiteForfeitButton;
    private JLabel playerBlackScore;
    private JLabel playerWhiteScore;


    Image chessPieceImages[][];

    //These numbers are determined by each sub image in the sprite-sheet
    //as fitted in the 2D Images Array
    public static final int QUEEN = 0;
    public static final int KING = 1;
    public static final int BISHOP = 2;
    public static final int KNIGHT = 3;
    public static final int ROOK = 4;
    public static final int PAWN = 5;
    public static final int BLACK = 0;
    public static final int WHITE = 1;



    public ChessGUI()
    {
        prepareGUI();
    }

    public static void main(String[] args){
        ChessGUI chessGame = new ChessGUI();
    }

    /**
     * Calls all the helper functions to create the initial
     * game plan
     */
    private void prepareGUI(){

        //Setting Look and Feel of the Frame
        setLookAndFeel();

        gameContainer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initializing Game Container and adding ChessBoard
        createAndAddGameContainer();
        createAndAddChessBoard();

        //Creating and adding Player Panes
        setPlayerBlackPane();
        setPlayerWhitePane();
        addPlayerPanes();

        //Creating Piece Images and adding them to the ChessBoard
        createImages();
        drawPiecesForNewGame();
        gameContainer.pack();

    }

    private void setLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, default LAF is used
        }
    }

    private void createAndAddGameContainer() {
        gameScreen = new JPanel(new BorderLayout(3, 3));
        gameScreen.setBorder(new EmptyBorder(5, 5, 5, 5));

        gameContainer.getContentPane().add(gameScreen, BorderLayout.CENTER);
    }

    private void createAndAddChessBoard() {
        chessBoard = new JPanel(new GridLayout(0, 9));
        //Black and White Board Boxes are created and added
        createChessBoxes();
        gameScreen.add(chessBoard, BorderLayout.CENTER);
    }

    private void addPlayerPanes() {
        playerPanes = new JSplitPane(JSplitPane.VERTICAL_SPLIT,playerBlack,playerWhite);
        playerPanes.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        gameContainer.getContentPane().add(playerPanes, BorderLayout.LINE_END);
        createToolbar();
    }

    private void setPlayerWhitePane() {
        playerWhite = new JPanel();
        playerWhite.setLayout(new BoxLayout(playerWhite, BoxLayout.Y_AXIS));
        playerWhiteName = new JLabel("Amaze");
        playerWhiteName.setFont(new Font("Arial", Font.BOLD, 65));
        playerWhite.add(playerWhiteName);
        try {
            //Online source for the Chess Piece Images
            URL url = new URL("https://upload.wikimedia.org/wikipedia/en/e/ee/Roadrunner_looney_tunes.png");
            BufferedImage bi = ImageIO.read(url);
            playerWhiteImage = new JLabel(new ImageIcon(bi));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Img Src Unavailable");
            System.exit(1);
        }
        playerWhiteImage.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));
        playerWhite.add(playerWhiteImage);
        playerWhiteForfeitButton = new JButton("FORFEIT");
        playerWhiteForfeitButton.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        playerWhiteForfeitButton.setPreferredSize(new Dimension(300, 100));
        playerWhiteForfeitButton.setFont(new Font("Arial", Font.BOLD, 40));
        playerWhite.add(playerWhiteForfeitButton);
        playerWhiteName.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerWhiteForfeitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerWhiteImage.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void setPlayerBlackPane() {
        playerBlack = new JPanel();
        playerBlack.setLayout(new BoxLayout(playerBlack, BoxLayout.Y_AXIS));
        playerBlackName = new JLabel("Rob");
        playerBlackName.setFont(new Font("Arial", Font.BOLD, 65));
        playerBlack.add(playerBlackName);
        try {
            //Online source for the Chess Piece Images
            URL url = new URL("https://upload.wikimedia.org/wikipedia/en/5/56/Wile_E_Coyote.png");
            BufferedImage bi = ImageIO.read(url);
            playerBlackImage = new JLabel(new ImageIcon(bi));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Img Src Unavailable");
            System.exit(1);
        }
        playerBlackImage.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));
        playerBlack.add(playerBlackImage);
        playerBlackForfeitButton = new JButton("FORFEIT");
        playerBlackForfeitButton.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        playerBlackForfeitButton.setPreferredSize(new Dimension(300, 100));
        playerBlackForfeitButton.setFont(new Font("Arial", Font.BOLD, 40));
        playerBlack.add(playerBlackForfeitButton);
        playerBlackName.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerBlackForfeitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerBlackImage.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void createToolbar() {
        newButton = new JButton("New");

        undoButton = new JButton("Undo");
        newButton.setPreferredSize(new Dimension(300, 100));

        undoButton.setPreferredSize(new Dimension(300, 100));

        undoButton.setFont(new Font("Arial", Font.PLAIN, 40));
        newButton.setFont(new Font("Arial", Font.PLAIN, 40));


        toolBar = new JPanel();
        toolBar.add(newButton);


        toolBar.add(undoButton);
        gameScreen.add(toolBar, BorderLayout.PAGE_START);
    }

    public void show()
    {
        //show the JFrame
        gameContainer.setVisible(true);
        gameContainer.setSize(1500, 1500);
    }

    /**
     * Creates and adds ChessBoxes to the ChessBoard
     */
    public void createChessBoxes(){
        LayoutManager layout = new GridLayout(8, 8);
        chessBoard.setLayout(layout);

        chessBoxes = new JButton[8][8];

        for(int rank = 7; rank >= 0; rank--){
            for(int col = 0; col < 8; col++){

                chessBoxes[rank][col] = new JButton();
                chessBoxes[rank][col].setOpaque(true);
                chessBoxes[rank][col].setBackground(getBoxColor(rank, col));
                chessBoxes[rank][col].setForeground(getBoxColor(rank, col));
                chessBoard.add(chessBoxes[rank][col]);
                chessBoxes[rank][col].setActionCommand(rank + " " + col);
            }
        }
    }

    public void addBoxListeners(ActionListener listener)
    {
        for(int i=0; i<8; i++)
        {
            for(int j=0;j<8;j++)
            {
                chessBoxes[i][j].addActionListener(listener);
            }
        }
    }



    /**
     * This function uses an online sprite sheet to extract piece images
     */
    public void createImages() {

        chessPieceImages = new Image[2][6];
        try {
            //Online source for the Chess Piece Images
            URL url = new URL("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Chess_Pieces_Sprite.svg/800px-Chess_Pieces_Sprite.svg.png");
            BufferedImage bi = ImageIO.read(url);
            for (int imageRank = 0; imageRank < 2; imageRank++) {
                for (int imageCol = 0; imageCol < 6; imageCol++) {
                    chessPieceImages[imageRank][imageCol] = bi.getSubimage(
                            imageCol * 133, imageRank * 133, 133, 133);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Img Src Unavailable");
            System.exit(1);
        }

        //special pieces
        //http://www.rhinoink.ca/rhinos/images/games/chess-piece.png
    }
    /**
     * This function returns the appropriate chessBoxes color
     *
     * @param rank
     * @param col
     * @return Color of the Box
     */
    public Color getBoxColor(int rank, int col){

        //Calculation for spot color
        if((rank + col) % 2 == 0)
            return new Color(102, 102, 102); //BLACK RGB
        else
            return new Color(255, 255, 255); //WHITE RGB
    }
    /**
     * This function gets the image from the chessPieceImages array and sets the appropriate chessBoxes's icon
     */
    public void drawPiecesForNewGame(){

        for(int rank = 7; rank>= 0; rank--){
            for(int col = 0; col < 8; col++){

                //WHITE PAWNS
                if(rank == 6){
                    chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[WHITE][PAWN]));
                }
                //BLACK PAWNS
                else if(rank == 1){
                    chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[BLACK][PAWN]));
                }
                //WHITE FACE PIECES
                else if(rank == 7){
                    if(col == 0 || col == 7)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[WHITE][ROOK]));
                    else if(col == 1 || col == 6)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[WHITE][KNIGHT]));
                    else if(col == 2 || col == 5)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[WHITE][BISHOP]));
                    else if(col == 4)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[WHITE][QUEEN]));
                    else if(col == 3)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[WHITE][KING]));
                }
                //BLACK FACE PIECES
                else if(rank == 0){
                    if(col == 0 || col == 7)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[BLACK][ROOK]));
                    else if(col == 1 || col == 6)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[BLACK][KNIGHT]));
                    else if(col == 2 || col == 5)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[BLACK][BISHOP]));
                    else if(col == 4)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[BLACK][QUEEN]));
                    else if(col == 3)
                        chessBoxes[rank][col].setIcon(new ImageIcon(chessPieceImages[BLACK][KING]));
                }
            }
        }
    }

    public void setBoxAsSelected(int rank, int col) {
        chessBoxes[rank][col].setBackground(Color.CYAN);
    }

    public void setBoxAsPossible(int rank, int col) {
        chessBoxes[rank][col].setBackground(Color.GREEN);
    }

    public void moveBox(int sourcerank, int sourceCol, int destrank, int destCol) {
        JButton source = chessBoxes[sourcerank][sourceCol];
        JButton destination = chessBoxes[destrank][destCol];
        Icon sourceIcon = source.getIcon();
        destination.setIcon(sourceIcon);
        source.setIcon(null);
    }

    public void setBoxesToNormal() {
        for(int i=0; i<8; i++)
        {
            for(int j=0;j<8;j++)
            {

                chessBoxes[i][j].setBackground(getBoxColor(i,j));
            }
        }
    }

    public void setBoxAsInDanger(int rank, int col) {
        chessBoxes[rank][col].setBackground(Color.RED);
    }

    public void changePlayerPaneColor(String playerColor)
    {
        if(playerColor.equalsIgnoreCase("black"))
        {
            playerBlack.setBackground(Color.YELLOW);
            playerWhite.setBackground(Color.LIGHT_GRAY);
        }
        else
        {
            playerWhite.setBackground(Color.YELLOW);
            playerBlack.setBackground(Color.LIGHT_GRAY);
        }
    }

    public void resetGame(boolean specialOrNot)
    {

    }

}
