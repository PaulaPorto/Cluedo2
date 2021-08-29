import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  Character class that represents the Characters on the board, which are the 6 characters.
 *  @authors antigonich singhsanj2 romcypaul alfredabra
 */

public class CluedoCharacter {

  //name of the character
  private String name;

  //the file name for that character image
  private String fileName;

  //the image of the charcter
  private BufferedImage img;

  //the tile at which the character occupies
  private Tile position;

  //the list of hand cards
  private List<Cards> handCards = new ArrayList<>();

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CluedoCharacter(String name, String fileName) {
    this.name = name;
    this.fileName = fileName;
    loadCharacter();
  }

  /**
   * Loads the image of the character using the filename given
   */
  public void loadCharacter() {
    try { img = ImageIO.read(getClass().getResourceAsStream(fileName)); }
    catch (IOException e) { e.printStackTrace(); }
  }

  /**
   * Draws the character on the board based on the given x and y position,
   * the width and height also gets specified.
   * @param x   the x position of the image
   * @param y   the y position of the image
   * @param tileWidth   the width of the image
   * @param tileHeight    the height of the image
   * @param gra     the graphics object used to draw the character
   */
  public void drawMethod(int x, int y, int tileWidth, int tileHeight, Graphics gra) {
    gra.drawImage(img, x, y, tileWidth, tileHeight, null);
  }

  /**
   * Returns the tile on which the character is standing on
   * @return  Tile object which is the position of the character
   */
  public Tile getPosition() {
    return position;
  }

  /**
   * Sets the position of the character's tile to keep track of where it is
   * @param position    Tile object that the character is on
   */
  public void setPosition(Tile position) {
    this.position = position;
  }

  /**
   * Returns the name of the character
   * @return    String that is the name of the character
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the list of cards that the character holds
   * @return    List of Card objects that represent the cards the character holds
   */
  public List<Cards> getHandCards(){
    return handCards;
  }
}

