//// new cluedo character class for ass2
//
//import javax.imageio.ImageIO;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.awt.Graphics;
//import java.io.IOException;
//
//public class CluedoCharacter {
//    private String name;
//    private String fileName;
//    private BufferedImage img;
//
//    public CluedoCharacter(String name, String fileName) {
//        this.name = name;
//        this.fileName = fileName;
//        loadCharacter();
//    }
//
//    public void loadCharacter() {
//        try { img = ImageIO.read(getClass().getResourceAsStream(fileName)); }
//        catch (IOException e) { e.printStackTrace(); }
//    }
//
//    public void drawMethod(int x, int y, int tileWidth, int tileHeight, Graphics gra) {
//        gra.drawImage(img, x, y, tileWidth, tileHeight, null);
//    }
//
//}

//25/08/20
// new cluedo character class for ass2

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CluedoCharacter {

  private String name;
  private String fileName;
  private BufferedImage img;
  private Tile position;
  private List<Cards> handCards = new ArrayList<>();

  public CluedoCharacter(String name, String fileName) {
    this.name = name;
    this.fileName = fileName;
    loadCharacter();
  }

  public void loadCharacter() {
    try { img = ImageIO.read(getClass().getResourceAsStream(fileName)); }
    catch (IOException e) { e.printStackTrace(); }
  }

  public void drawMethod(int x, int y, int tileWidth, int tileHeight, Graphics gra) {
    gra.drawImage(img, x, y, tileWidth, tileHeight, null);
  }

  public Tile getPosition() {
    return position;
  }

  public void setPosition(Tile position) {
    this.position = position;
  }

  public String getName() {
    return name;
  }

  public List<Cards> getHandCards(){
    return handCards;
  }
}

