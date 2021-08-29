/**
 * Tile class that determines the different types of tiles that are found on the board
 * i.e empty tile, room tile, wall tile etc.
 * @authors antigonich singhsanj2 romcypaul alfredabra
 */

public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //row and column of the tile
  private int row;
  private int col;

  //the character that represents the type of tile
  private Character character;

  //null if the character is not on the tile, otherwise has a character
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

  /**
   * Gets the character on the board
   * @return  the character
   */
  public CluedoCharacter getCluedoCharacter() {
    return cluedoCharacter;
  }

  /**
   * Set the character
   * @param cluedoCharacter
   */
  public void setCluedoCharacter(CluedoCharacter cluedoCharacter) {
    this.cluedoCharacter = cluedoCharacter;
  }
}
