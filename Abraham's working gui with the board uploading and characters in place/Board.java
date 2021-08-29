// 100% working as of 27-08-20
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
 * and the status of the board.
 */

public class Board {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //private Tile[][] arrayTile;
    private Tile[][] baseBoard;
    private static final String boardFilename = "Cluedo-Board.png";
    private BufferedImage b;
    private static final int offsetW = 7;
    private static final int offsetH = 7;
    private static final int boardWidth = 400;
    private static final int boardHeight = 420;
    private static final int boardTop = 0;
    private static final int boardLeft = 0;
    private static final int tileWidth = (boardWidth)/24;
    private static final int tileHeight= ((boardHeight)/25);
    public static List<CluedoCharacter> characterList = new ArrayList<>();

//    public CluedoCharacter getSc() {
//        return sc;
//    }
//
//    // In field because it can be accessed by anything
//    CluedoCharacter sc;
//    CluedoCharacter mu;
//    CluedoCharacter wh;
//    CluedoCharacter gr;
//    CluedoCharacter pe;
//    CluedoCharacter pl;


    private static final int rows = 25;
    private static final int cols = 24;

    /**
     * Variable for the board itself. This will help
     * stringbuilder read the board into the game.
     */
    String drawB =
            "XXXXXXXXwXXXXgXXXXXXXXXX"+
                    "XKKKXX___XBBX___XXCCCCCX"+
                    "XKKKX__XXBBBBXX__XCCCCCX"+
                    "XKKKX__XBBBBBBX__XCCCCCX"+
                    "XKKKX__XBBBBBBX__XCCCCCX"+
                    "XKKKX__~BBBBBB~___~XXXXX"+
                    "XXX~X__XBBBBBBX________P"+
                    "_______X~XXXX~X________X"+
                    "X__________________XXXXX"+
                    "XXXXX______________~RRRX"+
                    "XDDDXXXX__XXXXXX___XRRRX"+
                    "XDDDDDDX__XXXXXX___XRRRX"+
                    "XDDDDDD~__XXXXXX___XXX~X"+
                    "XDDDDDDX__XXXXXX________"+
                    "XDDDDDDX__XXXXXX___XX~XX"+
                    "XXXXXX~X__XXXXXX__XLLLLX"+
                    "X_________XXXXXX__~LLLLX"+
                    "m_________________XLLLLX"+
                    "X________XX~~XX____XXXXX"+
                    "XXXXX~X__XHHHHX________p"+
                    "XGGGGGX__XHHHH~________X"+
                    "XGGGGGX__XHHHHX__X~XXXXX"+
                    "XGGGGGX__XHHHHX__XSSSSSX"+
                    "XGGGGGX__XHHHHX__XSSSSSX"+
                    "XXXXXXXsXXXXXXXX_XXXXXXX";

    /**
     * Variable for the board without the players,
     * used for reverting the tiles back to what it was when a player moves out of it
     */
    private final String baseBoardNP =
            "XXXXXXXX_XXXX_XXXXXXXXXX"+
                    "XKKKXX___XBBX___XXCCCCCX"+
                    "XKKKX__XXBBBBXX__XCCCCCX"+
                    "XKKKX__XBBBBBBX__XCCCCCX"+
                    "XKKKX__XBBBBBBX__XCCCCCX"+
                    "XKKKX__~BBBBBB~___~XXXXX"+
                    "XXX~X__XBBBBBBX_________"+
                    "_______X~XXXX~X________X"+
                    "X__________________XXXXX"+
                    "XXXXX______________~RRRX"+
                    "XDDDXXXX__XXXXXX___XRRRX"+
                    "XDDDDDDX__XXXXXX___XRRRX"+
                    "XDDDDDD~__XXXXXX___XXX~X"+
                    "XDDDDDDX__XXXXXX________"+
                    "XDDDDDDX__XXXXXX___XX~XX"+
                    "XXXXXX~X__XXXXXX__XLLLLX"+
                    "X_________XXXXXX__~LLLLX"+
                    "__________________XLLLLX"+
                    "X________XX~~XX____XXXXX"+
                    "XXXXX~X__XHHHHX_________"+
                    "XGGGGGX__XHHHH~________X"+
                    "XGGGGGX__XHHHHX__X~XXXXX"+
                    "XGGGGGX__XHHHHX__XSSSSSX"+
                    "XGGGGGX__XHHHHX__XSSSSSX"+
                    "XXXXXXX_XXXXXXXX_XXXXXXX";


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Board()
    {
        //this.arrayTile = new Tile[rows][cols];
        this.baseBoard = new Tile[rows][cols];
    }

    //------------------------
    // INTERFACE
    //------------------------

    /**
     * Reads the board and populates the two 2d arrays with the strings
     */
//    public void readBoard() {
//        BufferedReader buff = new BufferedReader(new StringReader(drawB));
//        BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
//        for(int row = 0; row < rows; row++) {
//            for(int col = 0; col < cols; col++) {
//                try {
//                    arrayTile[row][col] = new Tile((char)buff.read(), row, col);
//                    this.baseBoard[row][col] = new Tile((char)buffy.read(), row, col);
//                }
//                catch (IOException e) { e.printStackTrace(); }
//            }
//        }
//    }

    public void loadBoard() {
        BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                try { this.baseBoard[row][col] = new Tile((char)buffy.read(), row, col); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

    public void loadImages() {
        try { b = ImageIO.read(getClass().getResourceAsStream(boardFilename)); }
        catch (IOException e) { e.printStackTrace(); }
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

//        sc = this.baseBoard[24][7].getCluedoCharacter();
//        mu = this.baseBoard[17][0].getCluedoCharacter();
//        wh = this.baseBoard[0][8].getCluedoCharacter();
//        gr = this.baseBoard[0][13].getCluedoCharacter();
//        pe = this.baseBoard[6][23].getCluedoCharacter();
//        pl = this.baseBoard[19][23].getCluedoCharacter();
    }

    public Tile findCurrentPos(String name) {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(baseBoard[row][col].getCluedoCharacter() != null) {
                    if(baseBoard[row][col].getCluedoCharacter().getName().equalsIgnoreCase(name)) {
                        return baseBoard[row][col];
                    }
                }
            }
        }
        return null;
    }

    public Tile getTileXandY(double x, double y) {
        int col = (int)((x - boardLeft - offsetW) / tileWidth);
        int row = (int)((y - boardTop - offsetH) / tileHeight);

        if(isValid(row, col)){
            Tile tile = baseBoard[row][col];
            System.out.println(row);
            System.out.println(col);
            System.out.println(x);
            System.out.println(y);
            return tile;
        }
        return null;
    }

    /**
     *  This method gets called on by the doMove method in the Game.java class.
     */
    public void move(Tile oldTile, Tile newTile) {
        CluedoCharacter currentCharacter = oldTile.getCluedoCharacter();
        oldTile.setCluedoCharacter(null);
        newTile.setCluedoCharacter(currentCharacter);
    }

    /**
     *  This method redraws the board.
     */
    public void redrawMethod(Graphics g) {
        g.drawImage(b,boardLeft,boardTop,boardWidth,boardHeight,null);
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < cols; col++) {
                if(baseBoard[row][col].getCluedoCharacter() != null) {
                    baseBoard[row][col].getCluedoCharacter().drawMethod(col * tileWidth + offsetW, row * tileHeight + offsetH, tileWidth, tileHeight, g);
                }
            }

        }
    }

    /**
     *   This method is called upon by the doMove method in the Game.java class.
     *   It checks if a move is valid or not.
     *   if a move is valid, it returns a list of tiles that the character will move across.
     *   Otherwise it returns null to indicate the move is not valid.
     **/
    public List<Tile> checkMove(Tile startTile, Tile goalTile, double dice) {
        int count = 0;
        List<Tile> moves = new ArrayList<>();
        Tile currentTile = startTile;
        if (goalTile == null){
            return null;
        }
        while(currentTile != goalTile) {

            // Variables to make life easier
            int clickedCol = goalTile.getCol();
            int currentCol = currentTile.getCol();
            int clickedRow = goalTile.getRow();
            int currentRow = currentTile.getRow();

            // cols
            if((clickedCol < currentCol) && baseBoard[currentRow][currentCol-1].getChar() != 'X' && (isValid(currentRow, currentCol-1))) {
                moves.add(baseBoard[currentRow][currentCol-1]);
                currentTile = baseBoard[currentRow][currentCol-1];
                count++;
            }
            else if((clickedCol > currentCol) && baseBoard[currentRow][currentCol+1].getChar() != 'X' && (isValid(currentRow, currentCol+1))) {
                moves.add(baseBoard[currentRow][currentCol+1]);
                currentTile = baseBoard[currentRow][currentCol+1];
                count++;
            }

            // rows
            else if((clickedRow < currentRow) && baseBoard[currentRow-1][currentCol].getChar() != 'X' && (isValid(currentRow-1, currentCol))) {
                moves.add(baseBoard[currentRow-1][currentCol]);
                currentTile = baseBoard[currentRow-1][currentCol];
                count++;
            }
            else if((clickedRow > currentRow) && baseBoard[currentRow+1][currentCol].getChar() != 'X' && (isValid(currentRow+1, currentCol))) {
                moves.add(baseBoard[currentRow+1][currentCol]);
                currentTile = baseBoard[currentRow+1][currentCol];
                count++;
            }
            else{
                return null;
            }
        }
        if(moves.size() < dice || count <= dice) { return moves; }
        else { return null; }
    }

    /**
     * Checks if the player's movement is valid or not based on different cases
     * @return  true if the player movement is valid, otherwise false
     */
    public boolean isValid(int r, int c){

        //if the row and column are out of bounds, the player cannot move
        if(r < 0){
            System.out.println("row is < 0");
            return false;
        }
        if(r > 24 ){
            System.out.println("row is > 24 (max index of array)");
            return false;
        }
        if(c < 0){
            System.out.println("col is < 0");
            return false;
        }
        if(c > 23){
            System.out.println("row is > 23 (max index of array)");
            return false;
        }
        return true;
    }


    /**
     * Prints the board to the console in a nice way
     */
//    public void printBoard() {
//        StringBuilder strBuilder = new StringBuilder();
//        for(int row = 0; row < rows; row++) {
//            strBuilder.append("|");
//            for(int col = 0; col < cols; col++) {
//                strBuilder.append(arrayTile[row][col].getChar());
//                strBuilder.append("|");
//            }
//            strBuilder.append("\n");
//        }
//        System.out.println(strBuilder.toString());
//    }

    /**
     * Prints the legend to the console in a nice way
     */
//    public void printLegend() {
//        System.out.println("Legends:\n" +
//                "~ = door\n" +
//                "X = wall\n" +
//                "\n" +
//                "K = Kitchen\n" +
//                "B = Ball Room\n" +
//                "C = Conservatory\n" +
//                "D = Dining Room\n" +
//                "R = Billiard Room\n" +
//                "G = Lounge\n" +
//                "H = Hall\n" +
//                "L = Library\n" +
//                "S = Study\n" +
//                "\n" +
//                "w = White\n" +
//                "g = Green\n" +
//                "P = Peacock\n" +
//                "p = Plum\n" +
//                "s = Scarlett\n" +
//                "m = Mustard\n");
//    }

    /**
     * Returns the arrayTile 2D array
     * @return  the 2D array which represents the current state of the board
     */
//    public Tile[][] getArrayTiles(){
//        return this.arrayTile;
//    }

    /**
     * Finds the letter in the board that is the same letter as the parameter
     * @param letter  the letter wanted to be searched
     * @return  the Tile in the location where the letter is
     */
//    public Tile findLetter(Character letter){
//        int count = 0;
//        for(int i = 0; i < rows; i++){
//            for(int j = 0; j < cols; j++){
//                count++;
//                //System.out.println(arrayTile[i][j].getRoom() + " " + count);
//                if(arrayTile[i][j].getChar().equals(letter)){
//                    return arrayTile[i][j];
//                }
//            }
//        }
//        return new Tile('+', 0, 0);
//    }

    /**
     * Move a piece from one position to another.
     * Need to Update. Work on getting the input from player on row and col. Set that to new position while the previous one is set to null
     * @param oldRow  the old row of the player
     * @param oldCol  the old col of the player
     * @param newRow  the new row of the player
     * @param newCol  the old row of the player
     */

//    public void move(int oldRow, int oldCol, int newRow, int newCol) {
//        char ch = arrayTile[oldRow][oldCol].getChar();
//        arrayTile[newRow][newCol] = new Tile(ch, newRow, newCol);
//        arrayTile[oldRow][oldCol] = new Tile(this.baseBoard[oldRow][oldCol].getChar(), oldRow, oldCol);
//
//        if(this.baseBoard[newRow][newCol].getChar().equals('K')) { System.out.println("You are in the room: Kitchen"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('G')) { System.out.println("You are in the room: Lounge"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('H')) { System.out.println("You are in the room: Hall"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('S')) { System.out.println("You are in the room: Study"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('C')) { System.out.println("You are in the room: Conservatory"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('D')) { System.out.println("You are in the room: Dining Room"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('B')) { System.out.println("You are in the room: Ball Room"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('R')) { System.out.println("You are in the room: Billiard Room"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('L')) { System.out.println("You are in the room: Library"); }
//    }

}

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.StringReader;
//
///**
// * Board class that contains the board state, the location of the player on the board
// * and the status of the board.
// */
//
//public class Board
//{
//
//    //------------------------
//    // MEMBER VARIABLES
//    //------------------------
//
//    private Tile[][] arrayTile;
//    private Tile[][] baseBoard;
//
//    private static final int rows = 25;
//    private static final int cols = 24;
//
//    /**
//     * Variable for the board itself. This will help
//     * stringbuilder read the board into the game.
//     */
//    String drawB =
//                    "XXXXXXXXwXXXXgXXXXXXXXXX"+
//                    "XKKKXX___XBBX___XXCCCCCX"+
//                    "XKKKX__XXBBBBXX__XCCCCCX"+
//                    "XKKKX__XBBBBBBX__XCCCCCX"+
//                    "XKKKX__XBBBBBBX__XCCCCCX"+
//                    "XKKKX__~BBBBBB~___~XXXXX"+
//                    "XXX~X__XBBBBBBX________P"+
//                    "_______X~XXXX~X________X"+
//                    "X__________________XXXXX"+
//                    "XXXXX______________~RRRX"+
//                    "XDDDXXXX__XXXXXX___XRRRX"+
//                    "XDDDDDDX__XXXXXX___XRRRX"+
//                    "XDDDDDD~__XXXXXX___XXX~X"+
//                    "XDDDDDDX__XXXXXX________"+
//                    "XDDDDDDX__XXXXXX___XX~XX"+
//                    "XXXXXX~X__XXXXXX__XLLLLX"+
//                    "X_________XXXXXX__~LLLLX"+
//                    "m_________________XLLLLX"+
//                    "X________XX~~XX____XXXXX"+
//                    "XXXXX~X__XHHHHX________p"+
//                    "XGGGGGX__XHHHH~________X"+
//                    "XGGGGGX__XHHHHX__X~XXXXX"+
//                    "XGGGGGX__XHHHHX__XSSSSSX"+
//                    "XGGGGGX__XHHHHX__XSSSSSX"+
//                    "XXXXXXXsXXXXXXXX_XXXXXXX";
//
//    /**
//     * Variable for the board without the players,
//     * used for reverting the tiles back to what it was when a player moves out of it
//     */
//    private final String baseBoardNP =
//                    "XXXXXXXX_XXXX_XXXXXXXXXX"+
//                    "XKKKXX___XBBX___XXCCCCCX"+
//                    "XKKKX__XXBBBBXX__XCCCCCX"+
//                    "XKKKX__XBBBBBBX__XCCCCCX"+
//                    "XKKKX__XBBBBBBX__XCCCCCX"+
//                    "XKKKX__~BBBBBB~___~XXXXX"+
//                    "XXX~X__XBBBBBBX_________"+
//                    "_______X~XXXX~X________X"+
//                    "X__________________XXXXX"+
//                    "XXXXX______________~RRRX"+
//                    "XDDDXXXX__XXXXXX___XRRRX"+
//                    "XDDDDDDX__XXXXXX___XRRRX"+
//                    "XDDDDDD~__XXXXXX___XXX~X"+
//                    "XDDDDDDX__XXXXXX________"+
//                    "XDDDDDDX__XXXXXX___XX~XX"+
//                    "XXXXXX~X__XXXXXX__XLLLLX"+
//                    "X_________XXXXXX__~LLLLX"+
//                    "__________________XLLLLX"+
//                    "X________XX~~XX____XXXXX"+
//                    "XXXXX~X__XHHHHX_________"+
//                    "XGGGGGX__XHHHH~________X"+
//                    "XGGGGGX__XHHHHX__X~XXXXX"+
//                    "XGGGGGX__XHHHHX__XSSSSSX"+
//                    "XGGGGGX__XHHHHX__XSSSSSX"+
//                    "XXXXXXX_XXXXXXXX_XXXXXXX";
//
//
//    //------------------------
//    // CONSTRUCTOR
//    //------------------------
//
//    public Board()
//    {
//        this.arrayTile = new Tile[rows][cols];
//        this.baseBoard = new Tile[rows][cols];
//    }
//
//    //------------------------
//    // INTERFACE
//    //------------------------
//
//    /**
//     * Reads the board and populates the two 2d arrays with the strings
//     */
//    public void readBoard() {
//        BufferedReader buff = new BufferedReader(new StringReader(drawB));
//        BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
//        for(int row = 0; row < rows; row++) {
//            for(int col = 0; col < cols; col++) {
//                try {
//                    arrayTile[row][col] = new Tile((char)buff.read(), row, col);
//                    this.baseBoard[row][col] = new Tile((char)buffy.read(), row, col);
//                }
//                catch (IOException e) { e.printStackTrace(); }
//            }
//        }
//    }
//
//    /**
//     * Prints the board to the console in a nice way
//     */
//    public void printBoard() {
//        StringBuilder strBuilder = new StringBuilder();
//        for(int row = 0; row < rows; row++) {
//            strBuilder.append("|");
//            for(int col = 0; col < cols; col++) {
//                strBuilder.append(arrayTile[row][col].getChar());
//                strBuilder.append("|");
//            }
//            strBuilder.append("\n");
//        }
//        System.out.println(strBuilder.toString());
//    }
//
//    /**
//     * Prints the legend to the console in a nice way
//     */
//    public void printLegend() {
//        System.out.println("Legends:\n" +
//                "~ = door\n" +
//                "X = wall\n" +
//                "\n" +
//                "K = Kitchen\n" +
//                "B = Ball Room\n" +
//                "C = Conservatory\n" +
//                "D = Dining Room\n" +
//                "R = Billiard Room\n" +
//                "G = Lounge\n" +
//                "H = Hall\n" +
//                "L = Library\n" +
//                "S = Study\n" +
//                "\n" +
//                "w = White\n" +
//                "g = Green\n" +
//                "P = Peacock\n" +
//                "p = Plum\n" +
//                "s = Scarlett\n" +
//                "m = Mustard\n");
//    }
//
//    /**
//     * Returns the arrayTile 2D array
//     * @return  the 2D array which represents the current state of the board
//     */
//    public Tile[][] getArrayTiles(){
//        return this.arrayTile;
//    }
//
//    /**
//     * Finds the letter in the board that is the same letter as the parameter
//     * @param letter  the letter wanted to be searched
//     * @return  the Tile in the location where the letter is
//     */
//    public Tile findLetter(Character letter){
//        int count = 0;
//        for(int i = 0; i < rows; i++){
//            for(int j = 0; j < cols; j++){
//                count++;
//                //System.out.println(arrayTile[i][j].getRoom() + " " + count);
//                if(arrayTile[i][j].getChar().equals(letter)){
//                    return arrayTile[i][j];
//                }
//            }
//        }
//        return new Tile('+', 0, 0);
//    }
//
//    /**
//     * Move a piece from one position to another.
//     * Need to Update. Work on getting the input from player on row and col. Set that to new position while the previous one is set to null
//     * @param oldRow  the old row of the player
//     * @param oldCol  the old col of the player
//     * @param newRow  the new row of the player
//     * @param newCol  the old row of the player
//     */
//    public void move(int oldRow, int oldCol, int newRow, int newCol) {
//        char ch = arrayTile[oldRow][oldCol].getChar();
//        arrayTile[newRow][newCol] = new Tile(ch, newRow, newCol);
//        arrayTile[oldRow][oldCol] = new Tile(this.baseBoard[oldRow][oldCol].getChar(), oldRow, oldCol);
//
//        if(this.baseBoard[newRow][newCol].getChar().equals('K')) { System.out.println("You are in the room: Kitchen"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('G')) { System.out.println("You are in the room: Lounge"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('H')) { System.out.println("You are in the room: Hall"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('S')) { System.out.println("You are in the room: Study"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('C')) { System.out.println("You are in the room: Conservatory"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('D')) { System.out.println("You are in the room: Dining Room"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('B')) { System.out.println("You are in the room: Ball Room"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('R')) { System.out.println("You are in the room: Billiard Room"); }
//        if(this.baseBoard[newRow][newCol].getChar().equals('L')) { System.out.println("You are in the room: Library"); }
//    }
//
//}


//old board.java
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.StringReader;
//import java.awt.image.BufferedImage;
//import java.awt.Graphics;
//import javax.swing.JPanel;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Board class that contains the board state, the location of the player on the board
// * and the status of the board.
// */
//
//public class Board {
////    List<Tile> movementList = new ArrayList<>();
////
////    Tile currentLoc;
////    Tile clickedLoc;
////
////    private static final double rows = 25.0;
////    private static final double cols = 24.0;
////
////    public double diceChecker {
////        while(currentLoc != clickedLoc) {
////            if(currentLoc.getCols() < clickedLoc.getCols()) {
////                if()currentLoc()-1).equals("X")) { break;}
////            }
////        }
////    }
////
////}
//
//    //------------------------
//    // MEMBER VARIABLES
//    //------------------------
//
//    //private Tile[][] arrayTile;
//    private Tile[][] baseBoard;
//    private static final String boardFilename = "Cluedo-Board.png";
//    private BufferedImage b;
//    private static final int offsetW = 7;
//    private static final int offsetH = 7;
//    private static final int boardWidth = 400;
//    private static final int boardHeight = 420;
//    private static final int boardTop = 0;
//    private static final int boardLeft = 0;
//    private static final int tileWidth = (boardWidth)/24;
//    private static final int tileHeight= ((boardHeight)/25);
//
//
//
//
//    private static final int rows = 25;
//    private static final int cols = 24;
//
//    /**
//     * Variable for the board itself. This will help
//     * stringbuilder read the board into the game.
//     */
//    String drawB =
//                    "XXXXXXXXwXXXXgXXXXXXXXXX"+
//                    "XKKKXX___XBBX___XXCCCCCX"+
//                    "XKKKX__XXBBBBXX__XCCCCCX"+
//                    "XKKKX__XBBBBBBX__XCCCCCX"+
//                    "XKKKX__XBBBBBBX__XCCCCCX"+
//                    "XKKKX__~BBBBBB~___~XXXXX"+
//                    "XXX~X__XBBBBBBX________P"+
//                    "_______X~XXXX~X________X"+
//                    "X__________________XXXXX"+
//                    "XXXXX______________~RRRX"+
//                    "XDDDXXXX__XXXXXX___XRRRX"+
//                    "XDDDDDDX__XXXXXX___XRRRX"+
//                    "XDDDDDD~__XXXXXX___XXX~X"+
//                    "XDDDDDDX__XXXXXX________"+
//                    "XDDDDDDX__XXXXXX___XX~XX"+
//                    "XXXXXX~X__XXXXXX__XLLLLX"+
//                    "X_________XXXXXX__~LLLLX"+
//                    "m_________________XLLLLX"+
//                    "X________XX~~XX____XXXXX"+
//                    "XXXXX~X__XHHHHX________p"+
//                    "XGGGGGX__XHHHH~________X"+
//                    "XGGGGGX__XHHHHX__X~XXXXX"+
//                    "XGGGGGX__XHHHHX__XSSSSSX"+
//                    "XGGGGGX__XHHHHX__XSSSSSX"+
//                    "XXXXXXXsXXXXXXXX_XXXXXXX";
//
//    /**
//     * Variable for the board without the players,
//     * used for reverting the tiles back to what it was when a player moves out of it
//     */
//    private final String baseBoardNP =
//                    "XXXXXXXX_XXXX_XXXXXXXXXX"+
//                    "XKKKXX___XBBX___XXCCCCCX"+
//                    "XKKKX__XXBBBBXX__XCCCCCX"+
//                    "XKKKX__XBBBBBBX__XCCCCCX"+
//                    "XKKKX__XBBBBBBX__XCCCCCX"+
//                    "XKKKX__~BBBBBB~___~XXXXX"+
//                    "XXX~X__XBBBBBBX_________"+
//                    "_______X~XXXX~X________X"+
//                    "X__________________XXXXX"+
//                    "XXXXX______________~RRRX"+
//                    "XDDDXXXX__XXXXXX___XRRRX"+
//                    "XDDDDDDX__XXXXXX___XRRRX"+
//                    "XDDDDDD~__XXXXXX___XXX~X"+
//                    "XDDDDDDX__XXXXXX________"+
//                    "XDDDDDDX__XXXXXX___XX~XX"+
//                    "XXXXXX~X__XXXXXX__XLLLLX"+
//                    "X_________XXXXXX__~LLLLX"+
//                    "__________________XLLLLX"+
//                    "X________XX~~XX____XXXXX"+
//                    "XXXXX~X__XHHHHX_________"+
//                    "XGGGGGX__XHHHH~________X"+
//                    "XGGGGGX__XHHHHX__X~XXXXX"+
//                    "XGGGGGX__XHHHHX__XSSSSSX"+
//                    "XGGGGGX__XHHHHX__XSSSSSX"+
//                    "XXXXXXX_XXXXXXXX_XXXXXXX";
//
//
//    //------------------------
//    // CONSTRUCTOR
//    //------------------------
//
//    public Board()
//    {
//        //this.arrayTile = new Tile[rows][cols];
//        this.baseBoard = new Tile[rows][cols];
//    }
//
//    //------------------------
//    // INTERFACE
//    //------------------------
//
//    /**
//     * Reads the board and populates the two 2d arrays with the strings
//     */
////    public void readBoard() {
////        BufferedReader buff = new BufferedReader(new StringReader(drawB));
////        BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
////        for(int row = 0; row < rows; row++) {
////            for(int col = 0; col < cols; col++) {
////                try {
////                    arrayTile[row][col] = new Tile((char)buff.read(), row, col);
////                    this.baseBoard[row][col] = new Tile((char)buffy.read(), row, col);
////                }
////                catch (IOException e) { e.printStackTrace(); }
////            }
////        }
////    }
//
//    public void loadBoard() {
//        BufferedReader buffy = new BufferedReader(new StringReader(baseBoardNP));
//        for(int row = 0; row < rows; row++) {
//            for(int col = 0; col < cols; col++) {
//                try { this.baseBoard[row][col] = new Tile((char)buffy.read(), row, col); }
//                catch (IOException e) { e.printStackTrace(); }
//            }
//        }
//    }
//
//    public void loadImages() {
//        try { b = ImageIO.read(getClass().getResourceAsStream(boardFilename)); }
//        catch (IOException e) { e.printStackTrace(); }
//    }
//
//    /**
//     * New movement class.
//     * Replaces the big movement class in game.
//     */
//    public void loadCharacters() {
//        CluedoCharacter scarlett = new CluedoCharacter("Miss Scarlett", "Scarlet.png");
//        CluedoCharacter mustard = new CluedoCharacter("Col. Mustard", "Mustard.png");
//        CluedoCharacter white = new CluedoCharacter("Mrs. White", "White.png");
//        CluedoCharacter green = new CluedoCharacter("Mr. Green", "Green.png");
//        CluedoCharacter peacock = new CluedoCharacter("Mrs. Peacock", "Peacock.png");
//        CluedoCharacter plum = new CluedoCharacter("Prof. Plum", "Plum.png");
//
//        // This is written by order the characters go on the board.
//        this.baseBoard[0][8].setCluedoCharacter(white);
//        this.baseBoard[0][13].setCluedoCharacter(green);
//        this.baseBoard[6][23].setCluedoCharacter(peacock);
//        this.baseBoard[17][0].setCluedoCharacter(mustard);
//        this.baseBoard[19][23].setCluedoCharacter(plum);
//        this.baseBoard[24][7].setCluedoCharacter(scarlett);
//    }
//
//    /**
//    *  This method gets called on by the doMove method in the Game.java class.
//    */
//    public void move(Tile oldTile, Tile newTile) {
//        CluedoCharacter currentCharacter = oldTile.getCluedoCharacter();
//        oldTile.setCluedoCharacter(null);
//        newTile.setCluedoCharacter(currentCharacter);
//    }
//
//    /**
//     *  This method redraws the board.
//     */
//    public void redrawMethod(Graphics g) {
//        g.drawImage(b,boardLeft,boardTop,boardWidth,boardHeight,null);
//        for(int row = 0; row < rows; row++) {
//            for(int col = 0; col < cols; col++) {
//                if(baseBoard[row][col].getCluedoCharacter() != null) {
//                    baseBoard[row][col].getCluedoCharacter().drawMethod(col * tileWidth + offsetW, row * tileHeight + offsetH, tileWidth, tileHeight, g);
//                }
//            }
//
//        }
//    }
//
//    /**
//    *   This method is called upon by the doMove method in the Game.java class.
//    *   It checks if a move is valid or not.
//    *   if a move is valid, it returns a list of tiles that the character will move across.
//    *   Otherwise it returns null to indicate the move is not valid.
//    **/
//    public List<Tile> checkMove(Tile startTile, Tile goalTile, double dice) {
//        List<Tile> moves = new ArrayList<>();
//        Tile currentTile = startTile;
//        while(currentTile != goalTile) {
//
//            // Variables to make life easier
//            int clickedCol = goalTile.getCol();
//            int currentCol = currentTile.getCol();
//            int clickedRow = goalTile.getRow();
//            int currentRow = currentTile.getRow();
//
//            // cols
//            if((clickedCol < currentCol) && baseBoard[rows][cols-1].getChar() != 'X') {
//                moves.add(baseBoard[rows][cols-1]);
//                currentTile = baseBoard[rows][cols-1];
//            }
//            else if((clickedCol > currentCol) && baseBoard[rows][cols+1].getChar() != 'X') {
//                moves.add(baseBoard[rows][cols+1]);
//                currentTile = baseBoard[rows][cols+1];
//            }
//
//            // rows
//            else if((clickedRow < currentRow) && baseBoard[rows-1][cols].getChar() != 'X') {
//                moves.add(baseBoard[rows-1][cols]);
//                currentTile = baseBoard[rows-1][cols];
//            }
//            else if((clickedRow > currentRow) && baseBoard[rows+1][cols].getChar() != 'X') {
//                moves.add(baseBoard[rows+1][cols]);
//                currentTile = baseBoard[rows+1][cols];
//            }
//        }
//        if(moves.size() < dice) { return moves; }
//        else { return null; }
//    }
//
//
//    /**
//     * Prints the board to the console in a nice way
//     */
////    public void printBoard() {
////        StringBuilder strBuilder = new StringBuilder();
////        for(int row = 0; row < rows; row++) {
////            strBuilder.append("|");
////            for(int col = 0; col < cols; col++) {
////                strBuilder.append(arrayTile[row][col].getChar());
////                strBuilder.append("|");
////            }
////            strBuilder.append("\n");
////        }
////        System.out.println(strBuilder.toString());
////    }
//
//    /**
//     * Prints the legend to the console in a nice way
//     */
////    public void printLegend() {
////        System.out.println("Legends:\n" +
////                "~ = door\n" +
////                "X = wall\n" +
////                "\n" +
////                "K = Kitchen\n" +
////                "B = Ball Room\n" +
////                "C = Conservatory\n" +
////                "D = Dining Room\n" +
////                "R = Billiard Room\n" +
////                "G = Lounge\n" +
////                "H = Hall\n" +
////                "L = Library\n" +
////                "S = Study\n" +
////                "\n" +
////                "w = White\n" +
////                "g = Green\n" +
////                "P = Peacock\n" +
////                "p = Plum\n" +
////                "s = Scarlett\n" +
////                "m = Mustard\n");
////    }
//
//    /**
//     * Returns the arrayTile 2D array
//     * @return  the 2D array which represents the current state of the board
//     */
////    public Tile[][] getArrayTiles(){
////        return this.arrayTile;
////    }
//
//    /**
//     * Finds the letter in the board that is the same letter as the parameter
//     * @param letter  the letter wanted to be searched
//     * @return  the Tile in the location where the letter is
//     */
////    public Tile findLetter(Character letter){
////        int count = 0;
////        for(int i = 0; i < rows; i++){
////            for(int j = 0; j < cols; j++){
////                count++;
////                //System.out.println(arrayTile[i][j].getRoom() + " " + count);
////                if(arrayTile[i][j].getChar().equals(letter)){
////                    return arrayTile[i][j];
////                }
////            }
////        }
////        return new Tile('+', 0, 0);
////    }
//
//    /**
//     * Move a piece from one position to another.
//     * Need to Update. Work on getting the input from player on row and col. Set that to new position while the previous one is set to null
//     * @param oldRow  the old row of the player
//     * @param oldCol  the old col of the player
//     * @param newRow  the new row of the player
//     * @param newCol  the old row of the player
//     */
//
////    public void move(int oldRow, int oldCol, int newRow, int newCol) {
////        char ch = arrayTile[oldRow][oldCol].getChar();
////        arrayTile[newRow][newCol] = new Tile(ch, newRow, newCol);
////        arrayTile[oldRow][oldCol] = new Tile(this.baseBoard[oldRow][oldCol].getChar(), oldRow, oldCol);
////
////        if(this.baseBoard[newRow][newCol].getChar().equals('K')) { System.out.println("You are in the room: Kitchen"); }
////        if(this.baseBoard[newRow][newCol].getChar().equals('G')) { System.out.println("You are in the room: Lounge"); }
////        if(this.baseBoard[newRow][newCol].getChar().equals('H')) { System.out.println("You are in the room: Hall"); }
////        if(this.baseBoard[newRow][newCol].getChar().equals('S')) { System.out.println("You are in the room: Study"); }
////        if(this.baseBoard[newRow][newCol].getChar().equals('C')) { System.out.println("You are in the room: Conservatory"); }
////        if(this.baseBoard[newRow][newCol].getChar().equals('D')) { System.out.println("You are in the room: Dining Room"); }
////        if(this.baseBoard[newRow][newCol].getChar().equals('B')) { System.out.println("You are in the room: Ball Room"); }
////        if(this.baseBoard[newRow][newCol].getChar().equals('R')) { System.out.println("You are in the room: Billiard Room"); }
////        if(this.baseBoard[newRow][newCol].getChar().equals('L')) { System.out.println("You are in the room: Library"); }
////    }
//
//}
