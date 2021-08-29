/**
 * Tile class that determines the different types of tiles that are found on the board
 * i.e empty tile, room tile, wall tile etc.
 */

//public class Tile {

//  private double rows;
//  private double cols;
//  //private Tile [][] arrTile;
//
//
//  public double getRows() {
//    return rows;
//  }
//
//  public double getCols() {
//    return cols;
//  }
//
//  public void setRows(double rows) {
//    this.rows = rows;
//  }
//
//  public void setCols(double cols) {
//    this.cols = cols;
//  }
//
//}

public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  private int row;
  private int col;
  private Character character;
  private CluedoCharacter cluedoCharacter = null;
  //private double width;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(Character ch, int row, int col) {
    this.character = ch;
    this.row = row;
    this.col = col;
  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * Returns the row position of the tile
   * @return  the row position
   */
  public int getRow(){
    return this.row;
  }

  /**
   * Returns the col position of the tile
   * @return  the col position
   */
  public int getCol(){
    return this.col;
  }

  /**
   * Returns the character associated with the tile object
   * @return  the character associated with the tile
   */
  public Character getChar()
  {
    return this.character;
  }

  public CluedoCharacter getCluedoCharacter() {
    return cluedoCharacter;
  }

  public void setCluedoCharacter(CluedoCharacter cluedoCharacter) {
    this.cluedoCharacter = cluedoCharacter;
  }

}

//
///**
// * Tile class that determines the different types of tiles that are found on the board
// * i.e empty tile, room tile, wall tile etc.
// */
//
//public class Tile
//{
//
//  //------------------------
//  // MEMBER VARIABLES
//  //------------------------
//
//  private int row;
//  private int col;
//  private Character character;
//
//  //------------------------
//  // CONSTRUCTOR
//  //------------------------
//
//  public Tile(Character ch, int row, int col) {
//    this.character = ch;
//    this.row = row;
//    this.col = col;
//  }
//
//  //------------------------
//  // INTERFACE
//  //------------------------
//
//  /**
//   * Returns the row position of the tile
//   * @return  the row position
//   */
//  public int getRow(){
//    return this.row;
//  }
//
//  /**
//   * Returns the col position of the tile
//   * @return  the col position
//   */
//  public int getCol(){
//    return this.col;
//  }
//
//  /**
//   * Returns the character associated with the tile object
//   * @return  the character associated with the tile
//   */
//  public Character getChar()
//  {
//    return this.character;
//  }
//
//}