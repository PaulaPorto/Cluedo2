import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Board class that contains the board state, the location of the player on the board
 * and the status of the board and some logic of movement of each character.
 * @authors antigonich singhsanj2 romcypaul alfredabra
 */

public class Board {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //the base board without the characters
    private Tile[][] baseBoard;

    //the image name for the board
    private static final String boardFilename = "Cluedo-Board.png";

    //the buffered image of the board
    private BufferedImage b;

    //the offsets for calculating the position
    private static final int offsetW = 7;
    private static final int offsetH = 7;

    //the board width and height
    private static final int boardWidth = 400;
    private static final int boardHeight = 420;

    //the starting point of the board (0, 0)
    private static final int boardTop = 0;
    private static final int boardLeft = 0;

    //the tile width and height
    private static final int tileWidth = (boardWidth) / 24;
    private static final int tileHeight = ((boardHeight) / 25);

    //list of characters that are on the board
    public static List<CluedoCharacter> characterList = new ArrayList<>();

    //the rows and columns of the board (25 x 24)
    private static final int rows = 25;
    private static final int cols = 24;

    /**
     * Variable for the board without the players
     */
    private final String baseBoardNP =
            "XXXXXXXX_XXXX_XXXXXXXXXX" +
                    "XKKKXX___XBBX___XXCCCCCX" +
                    "XKKKX__XXBBBBXX__XCCCCCX" +
                    "XKKKX__XBBBBBBX__XCCCCCX" +
                    "XKKKX__XBBBBBBX__XCCCCCX" +
                    "XKKKX__~BBBBBB~___~XXXXX" +
                    "XXX~X__XBBBBBBX_________" +
                    "_______X~XXXX~X________X" +
                    "X__________________XXXXX" +
                    "XXXXX______________~RRRX" +
                    "XDDDXXXX__XXXXXX___XRRRX" +
                    "XDDDDDDX__XXXXXX___XRRRX" +
                    "XDDDDDD~__XXXXXX___XXX~X" +
                    "XDDDDDDX__XXXXXX________" +
                    "XDDDDDDX__XXXXXX___XX~XX" +
                    "XXXXXX~X__XXXXXX__XLLLLX" +
                    "X_________XXXXXX__~LLLLX" +
                    "__________________XLLLLX" +
                    "X________XX~~XX____XXXXX" +
                    "XXXXX~X__XHHHHX_________" +
                    "XGGGGGX__XHHHH~________X" +
                    "XGGGGGX__XHHHHX__X~XXXXX" +
                    "XGGGGGX__XHHHHX__XSSSSSX" +
                    "XGGGGGX__XHHHHX__XSSSSSX" +
                    "XXXXXXX_XXXXXXXX_XXXXXXX";


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Board() {
        //this.arrayTile = new Tile[rows][cols];
        this.baseBoard = new Tile[rows][cols];
    }

    //------------------------
    // INTERFACE
    //------------------------

    /**
     * Loads the board into the GUI
     */
    public void loadBoard() {
        BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                try {
                    this.baseBoard[row][col] = new Tile((char) buffy.read(), row, col);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads the images of the board
     */
    public void loadImages() {
        try {
            b = ImageIO.read(getClass().getResourceAsStream(boardFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This class loads the characters.
     */
    public void loadCharacters() {
        // This is written by order the characters go on the board.
        CluedoCharacter scarlett = new CluedoCharacter("Miss Scarlett", "Scarlet.png");
        CluedoCharacter mustard = new CluedoCharacter("Col. Mustard", "Mustard.png");
        CluedoCharacter white = new CluedoCharacter("Mrs. White", "White.png");
        CluedoCharacter green = new CluedoCharacter("Mr. Green", "Green.png");
        CluedoCharacter peacock = new CluedoCharacter("Mrs. Peacock", "Peacock.png");
        CluedoCharacter plum = new CluedoCharacter("Prof. Plum", "Plum.png");
        this.baseBoard[0][8].setCluedoCharacter(white);
        this.baseBoard[0][13].setCluedoCharacter(green);
        this.baseBoard[6][23].setCluedoCharacter(peacock);
        this.baseBoard[17][0].setCluedoCharacter(mustard);
        this.baseBoard[19][23].setCluedoCharacter(plum);
        this.baseBoard[24][7].setCluedoCharacter(scarlett);
        white.setPosition(baseBoard[0][8]);
        green.setPosition(baseBoard[0][13]);
        peacock.setPosition(baseBoard[6][23]);
        mustard.setPosition(baseBoard[17][0]);
        plum.setPosition(baseBoard[19][23]);
        scarlett.setPosition(baseBoard[24][7]);

        characterList.add(scarlett);
        characterList.add(mustard);
        characterList.add(white);
        characterList.add(green);
        characterList.add(peacock);
        characterList.add(plum);
    }

    /**
     * Finds the current position of the character
     * @param name  the name of the character wanting to be found
     * @return      the Tile object where the character is on
     */
    public Tile findCurrentPos(String name) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (baseBoard[row][col].getCluedoCharacter() != null) {
                    if (baseBoard[row][col].getCluedoCharacter().getName().equalsIgnoreCase(name)) {
                        return baseBoard[row][col];
                    }
                }
            }
        }
        return null;
    }

    /**
     * Calculates the tile position from the x and y value,
     * which is taken from where the user has clicked on the board
     * @param x     the x value of the click the user did
     * @param y     the y value of the click the user did
     * @return      the Tile object that corresponds to the clicked spot
     */
    public Tile getTileXandY(double x, double y) {
        int col = (int) ((x - boardLeft - offsetW) / tileWidth);
        int row = (int) ((y - boardTop - offsetH) / tileHeight);

        if (isValid(row, col)) {
            Tile tile = baseBoard[row][col];
            return tile;
        }
        return null;
    }

    /**
     * This method gets called on by the doMove method in the Game.java class.
     */
    public void move(Tile oldTile, Tile newTile) {
        CluedoCharacter currentCharacter = oldTile.getCluedoCharacter();
        oldTile.setCluedoCharacter(null);
        newTile.setCluedoCharacter(currentCharacter);
    }

    /**
     * This method redraws the board.
     */
    public void redrawMethod(Graphics g) {
        g.drawImage(b, boardLeft, boardTop, boardWidth, boardHeight, null);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (baseBoard[row][col].getCluedoCharacter() != null) {
                    baseBoard[row][col].getCluedoCharacter().drawMethod(col * tileWidth + offsetW, row * tileHeight + offsetH, tileWidth, tileHeight, g);
                }
            }

        }
    }

    /**
     * This method is called upon by the doMove method in the Game.java class.
     * It checks if a move is valid or not.
     * if a move is valid, it returns a list of tiles that the character will move across.
     * Otherwise it returns null to indicate the move is not valid.
     **/
    public List<Tile> checkMove(Tile startTile, Tile goalTile, double dice) {
        int count = 0;
        List<Tile> moves = new ArrayList<>();
        Tile currentTile = startTile;
        if (goalTile == null) {
            return null;
        }
        while (currentTile != goalTile) {

            int clickedCol = goalTile.getCol();
            int currentCol = currentTile.getCol();
            int clickedRow = goalTile.getRow();
            int currentRow = currentTile.getRow();
            // cols
            if ((clickedCol < currentCol) && baseBoard[currentRow][currentCol - 1].getChar() != 'X' && (isValid(currentRow, currentCol - 1))) {
                moves.add(baseBoard[currentRow][currentCol - 1]);
                currentTile = baseBoard[currentRow][currentCol - 1];
                count++;
            } else if ((clickedCol > currentCol) && baseBoard[currentRow][currentCol + 1].getChar() != 'X' && (isValid(currentRow, currentCol + 1))) {
                moves.add(baseBoard[currentRow][currentCol + 1]);
                currentTile = baseBoard[currentRow][currentCol + 1];
                count++;
            }
            // rows
            else if ((clickedRow < currentRow) && baseBoard[currentRow - 1][currentCol].getChar() != 'X' && (isValid(currentRow - 1, currentCol))) {
                moves.add(baseBoard[currentRow - 1][currentCol]);
                currentTile = baseBoard[currentRow - 1][currentCol];
                count++;
            } else if ((clickedRow > currentRow) && baseBoard[currentRow + 1][currentCol].getChar() != 'X' && (isValid(currentRow + 1, currentCol))) {
                moves.add(baseBoard[currentRow + 1][currentCol]);
                currentTile = baseBoard[currentRow + 1][currentCol];
                count++;
            } else {
                return null;
            }
        }
        if (moves.size() < dice || count <= dice) {
            return moves;
        } else {
            return null;
        }
    }

    /**
     * Checks if the player's movement is valid or not based on different cases
     *
     * @return true if the player movement is valid, otherwise false
     */
    public boolean isValid(int r, int c) {
        //if the row and column are out of bounds, the player cannot move
        if (r < 0) { return false; }
        if (r > 24) { return false; }
        if (c < 0) { return false; }
        if (c > 23) { return false; }
        return true;
    }
}