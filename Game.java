import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Game class that contains the logic behind the game itself, player actions,
 * initialising the game, handing and shuffling cards etc.
 * @authors antigonich singhsanj2 romcypaul alfredabra
 */

public class Game extends GUI{

    public static final int min = 2;
    public static final int max = 12;
    public static int diceValue = 0;

    //boolean to check if player has won the game, OR possibly using an enum instead
    private boolean win = false;

    //the current game state
    private GameState currentState = GameState.ONGOING;

    //the murder circumstances that needs to be guessed
    private List<Cards> murderCircumstances = new ArrayList<>();

    //lists that contain different types of Cards
    private List<CharacterCard> characters = new ArrayList<>();
    private List<WeaponCard> weapons = new ArrayList<>();
    private List<RoomCard> rooms = new ArrayList<>();

    //stack of cards that will be dealt out to players
    private Stack<Cards> dealingOut = new Stack<>();

    //the game board
    private Board board;

    //the current player who's turn it is
    public static String currentP;
    public static String currentCharacter;
    private Tile currentPos;

    //number of players playing
    public static int totalPlaying;

    //the number of players and characters total
    public static int totalNamesAndCharacters;

    //input Scanner
    private static Scanner input = new Scanner(System.in);

    //private Graphics graph;
    public static boolean awaitingClick = true;
    public static boolean invalidMove = false;
    private double clickedX, clickedY;

    public static List<String> nameOfPlayers = new ArrayList<>();
    public static List<String> nameOfCharacters = new ArrayList<>();
    private List<String> orderedPlayerNames = new ArrayList<>();
    private List<String> orderedCharacters = new ArrayList<>();
    private final List<String> playOrder = new ArrayList<>(Arrays.asList("Miss Scarlett",
            "Col. Mustard",
            "Mrs. White",
            "Mr. Green",
            "Mrs. Peacock",
            "Prof. Plum"));
    private int playerNum = 0;

    private boolean move = true;
    public static int movesDone = 1;

    @Override
    protected void redraw(Graphics g) {
        //graph = g;
        board.redrawMethod(g);
    }

    @Override
    protected void onClick(MouseEvent e) {
        Point point = e.getPoint();
        clickedX = point.getX();
        clickedY = point.getY();
        awaitingClick = false;
    }

    @Override
    protected List<Integer> rollDice() {
        List<Integer> rolledNumbers = new ArrayList<>();
        int dice1 = (int)(Math.random() * (6 - 1 + 1) + 1);
        int dice2 = (int)(Math.random() * (6 - 1 + 1) + 1);

        rolledNumbers.add(dice1);
        rolledNumbers.add(dice2);

        diceValue = dice1+dice2;
        return rolledNumbers;
    }

    @Override
    public void orderPlayersAndCharacters(){
        int count = 0;

        for(int i = 0; i < playOrder.size(); i++){
            for(int j = 0; j < nameOfCharacters.size(); j++){
                if(nameOfCharacters.get(j).equalsIgnoreCase(playOrder.get(i))){
                    orderedPlayerNames.add(count, nameOfPlayers.get(j));
                    orderedCharacters.add(count, nameOfCharacters.get(j));
                    playOrder.remove(i);
                    count++;

                }
            }
        }

        totalPlaying = orderedCharacters.size();
        totalNamesAndCharacters = orderedPlayerNames.size() + orderedCharacters.size();
    }

    @Override
    public void endTurn(){
        int totalPlayers = orderedCharacters.size();
        playerNum++;
        if(playerNum == totalPlayers){
            playerNum = 0;
        }
        currentP = orderedPlayerNames.get(playerNum);
        currentCharacter = orderedCharacters.get(playerNum);
        movesDone = 1;
    }

    /**
     * Constructor for the game
     */
    public Game() {
        super();
        this.board = new Board();
        this.board.loadBoard();
        this.board.loadImages();
        this.board.loadCharacters();
    }

    /**
     * Deals out the cards to the players according to the number of players playing in the game
     */
    @Override
    public void initialiseCards(){
        RoomCard room1 = new RoomCard("Kitchen", "Kitchen.png");
        RoomCard room2 = new RoomCard("Ball Room", "Ball_Room.png");
        RoomCard room3 = new RoomCard("Conservatory", "Conservatory.png");
        RoomCard room4 = new RoomCard("Billiard Room", "Billiard.png");
        RoomCard room5 = new RoomCard("Library", "Library.png");
        RoomCard room6 = new RoomCard("Study", "Study.png");
        RoomCard room7 = new RoomCard("Hall", "Hall.png");
        RoomCard room8 = new RoomCard("Lounge", "Lounge.png");
        RoomCard room9 = new RoomCard("Dining Room", "Dining.png");
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);
        rooms.add(room7);
        rooms.add(room8);
        rooms.add(room9);

        WeaponCard weapon1 = new WeaponCard("Candlestick", "Candlestick.png");
        WeaponCard weapon2 = new WeaponCard("Dagger", "Dagger.png");
        WeaponCard weapon3 = new WeaponCard("Lead Pipe", "Lead-Pipe.png");
        WeaponCard weapon4 = new WeaponCard("Revolver", "Revolver.png");
        WeaponCard weapon5 = new WeaponCard("Rope", "Rope.png");
        WeaponCard weapon6 = new WeaponCard("Spanner", "Spanner.png");
        weapons.add(weapon1);
        weapons.add(weapon2);
        weapons.add(weapon3);
        weapons.add(weapon4);
        weapons.add(weapon5);
        weapons.add(weapon6);

        CharacterCard character1 = new CharacterCard("Scarlett", "Miss.Scarlet.png");
        CharacterCard character2 = new CharacterCard("Mustard", "Colonel.Mustard.png");
        CharacterCard character3 = new CharacterCard("White", "Mrs.White.png");
        CharacterCard character4 = new CharacterCard("Green", "Mr.Green.png");
        CharacterCard character5 = new CharacterCard("Peacock", "Mrs.Peacock.png");
        CharacterCard character6 = new CharacterCard("Plum", "Professor.Plum.png");
        characters.add(character1);
        characters.add(character2);
        characters.add(character3);
        characters.add(character4);
        characters.add(character5);
        characters.add(character6);
    }

    /**
     * Method that initialises the game, picks the murder circumstances, gives the cards out to players etc.
     */
    public void initialiseGame(){
        this.currentState = GameState.ONGOING;
    }

    @Override
    public void setMurderCircumstances(){
        //gets a random number between 1 and 6 (inclusive)
        double randomChar = 0 + (Math.random() * 5);

        //gets a random number between 1 and 6 (inclusive)
        double randomWeapon = 0 + (Math.random() * 5);

        //gets a random number between 1 and 9 (inclusive)
        double randomRoom = 0 + (Math.random() * 8);

        //assigns the murder circumstances and removes them from their respective lists
        CharacterCard murderChar = this.characters.get((int)randomChar);
        this.characters.remove(murderChar);

        WeaponCard murderWeapon = this.weapons.get((int)randomWeapon);
        this.weapons.remove(murderWeapon);

        RoomCard murderRoom = this.rooms.get((int)randomRoom);
        this.rooms.remove(murderRoom);

        //adds the murder circumstances to the mCircumstances list
        this.murderCircumstances.add(murderChar);
        this.murderCircumstances.add(murderWeapon);
        this.murderCircumstances.add(murderRoom);
    }

    @Override
    public void setHandCards()
    {
        //adds the remaining cards into the handCards stack
        this.dealingOut.addAll(this.characters);
        this.dealingOut.addAll(this.weapons);
        this.dealingOut.addAll(this.rooms);

        //shuffles the cards
        Collections.shuffle(this.dealingOut);
    }

    @Override
    protected void dealCards() {
        switch(totalPlaying){
            case 3:
                for(int i = 0; i < Board.characterList.size(); i++){
                    for(int j = 0; j < orderedCharacters.size(); j++){
                        if(orderedCharacters.get(j).equalsIgnoreCase(Board.characterList.get(i).getName())){
                            for(int k = 0; k < 6; k++){
                                Board.characterList.get(i).getHandCards().add(dealingOut.pop());
                            }
                        }
                    }
                }
                break;
            case 4:
                CluedoCharacter p1 = null;
                CluedoCharacter p2 = null;
                CluedoCharacter p3 = null;
                CluedoCharacter p4 = null;

                for(int i = 0; i < Board.characterList.size(); i++){
                    if(orderedCharacters.get(0).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p1 = Board.characterList.get(i);
                    }
                    if(orderedCharacters.get(1).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p2 = Board.characterList.get(i);
                    }
                    if(orderedCharacters.get(2).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p3 = Board.characterList.get(i);
                    }
                    if(orderedCharacters.get(3).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p4 = Board.characterList.get(i);
                    }
                }

                for(int i = 0 ; i < 4; i++){
                    p1.getHandCards().add(dealingOut.pop());
                }

                for(int i = 0 ; i < 4; i++){
                    p2.getHandCards().add(dealingOut.pop());
                }

                for(int i = 0 ; i < 5; i++){
                    p3.getHandCards().add(dealingOut.pop());
                }

                for(int i = 0 ; i < 5; i++){
                    p4.getHandCards().add(dealingOut.pop());
                }
                break;
            case 5:
                p1 = null;
                p2 = null;
                p3 = null;
                p4 = null;
                CluedoCharacter p5 = null;

                for(int i = 0; i < Board.characterList.size(); i++){
                    if(orderedCharacters.get(0).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p1 = Board.characterList.get(i);
                    }
                    if(orderedCharacters.get(1).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p2 = Board.characterList.get(i);
                    }
                    if(orderedCharacters.get(2).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p3 = Board.characterList.get(i);
                    }
                    if(orderedCharacters.get(3).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p4 = Board.characterList.get(i);
                    }
                    if(orderedCharacters.get(4).equalsIgnoreCase(Board.characterList.get(i).getName())){
                        p5 = Board.characterList.get(i);
                    }
                }

                for(int i = 0 ; i < 3; i++){
                    p1.getHandCards().add(dealingOut.pop());
                }

                for(int i = 0 ; i < 3; i++){
                    p2.getHandCards().add(dealingOut.pop());
                }

                for(int i = 0 ; i < 4; i++){
                    p3.getHandCards().add(dealingOut.pop());
                }

                for(int i = 0 ; i < 4; i++){
                    p4.getHandCards().add(dealingOut.pop());
                }

                for(int i = 0 ; i < 4; i++){
                    p5.getHandCards().add(dealingOut.pop());
                }
                break;
            case 6:
                for(int i  = 0; i < Board.characterList.size(); i++){
                    for(int j = 0; j < orderedCharacters.size(); j++){
                        if(orderedCharacters.get(j) == Board.characterList.get(i).getName()){
                            for(int k = 0; k < 3; k++){
                                Board.characterList.get(i).getHandCards().add(dealingOut.pop());
                            }
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void doMove() {
        currentPos = board.findCurrentPos(currentCharacter);
        if(currentPos == null) {}
        while(true) {
            while(awaitingClick) {

            }
            Tile newPos = board.getTileXandY(clickedX, clickedY);
            List<Tile> moves = board.checkMove(currentPos, newPos, diceValue);

            // Checking if moves are valid.
            if(moves != null && newPos.getCluedoCharacter() == null) {
                invalidMove = false; //notifies the player with a popup
                board.move(currentPos, moves.get(0));
                redraw();
                for(int i = 0; i < moves.size()-1; i++) {
                    board.move(moves.get(i), moves.get(i+1));
                    movesDone++;
                    System.out.println(movesDone);
                    redraw();
                }
                newPos.getCluedoCharacter().setPosition(newPos);
                break;
            }
            else{
                invalidMove = true;
                break;
            }
        }
    }

    public static void main(String[] arguments){
        new Game();

    }
}
