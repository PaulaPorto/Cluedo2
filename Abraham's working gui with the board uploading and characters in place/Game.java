// 100% working as of 27/08/20
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class Game extends GUI{

    public static final int min = 2;
    public static final int max = 12;
    public static int diceValue = 0;

    public String numPeeps;

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

    //players playing
    private List<Player> players = new ArrayList<>();

    //stack of cards that will be dealt out to players
    private Stack<Cards> dealingOut = new Stack<>();

    //the game board
    private Board board;

    //the current player who's turn it is
    private PlayableCharacters currentPlayer;

    //private CluedoCharacter currentCharacter = board.getSc();

    //Position of current player
    //was previously "Miss Scarlett"
    public static String currentP;
    public static String currentCharacter;
    private Tile currentPos;

    public static int totalPlaying;

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
        System.out.println(clickedX);
        System.out.println(clickedY);
        awaitingClick = false;

        //may want to put in an if statement so that awaitingClick is only set to false
        // if its within a specified region.
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
                    count++;

                }
            }
        }

        totalPlaying = orderedCharacters.size();

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

    }

    /**
     * Constructor for the game
     */
    public Game() {
        super();
        //creates the board and prints it out

        System.out.println("hi");
        this.board = new Board();
        this.board.loadBoard();
        this.board.loadImages();
        this.board.loadCharacters();
        //currentCharacter = board.getScarlett();
        //this.board.redrawMethod(graph);
        System.out.println("hi");
        //this.board.readBoard();
        //this.board.printLegend();
        //this.board.printBoard();

//        List<Player> players = createPlayers();
//        initialiseGame(players);
        //    initialiseCards();
        //    setMurderCircumstances();
        //    setHandCards();
        //    dealOutCards();


        while(this.currentState == GameState.ONGOING){
            printHandCards();
            askRoll();
            doMove();
            System.out.println("click");

            //this.board.printBoard();
            //this.board.printBoard();
            break;
        }
    }

    /**
     * Asks the player to move, and also does the moving of the player
     */
//        public void askMove(){
//            boolean bool = false;
//            int counter = 0;
//            int moves = this.diceValue - counter;
//            while(!bool){
//                System.out.println("---------------------------------------------------------------------");
//                System.out.println("According to your dice value, type in your desired moves (U for up, D for down, L for left, R for right) " +
//                        "\nFor example, if you rolled a 3, your input should look something like LLU, this would move you left, left, up.");
//                String movesInput = this.input.next();
//                for(int i = 0; i < movesInput.length(); i++){
//                    if(movesInput.length() > this.diceValue){
//                        System.out.println("Input does mot match your number of moves (dice value). Try again :/");
//                        System.out.println(counter);
//                        break;
//                    }
//                    if(counter >= this.diceValue){
//                        System.out.println("The amount of moves you have done has exceeded the dice value, the moves made that exceeded have been ignored");
//                        counter = 0;
//                        break;
//                    }
//                    if(counter < this.diceValue) {
//                        if(movesInput.charAt(i) == ('R') || movesInput.charAt(i) == ('r')){
//                        Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
//                        if(isValid(currentPlayerPos.getRow(), currentPlayerPos.getCol() + 1)){
//                                this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow(), currentPlayerPos.getCol() + 1);
//                                counter++;
//                                System.out.println("Moved right!");
//                                bool = true;
//                        }
//                        else{
//                            this.board.printBoard();
//                            System.out.println("Invalid right :(");
//                            bool = false;
//                            break;
//                        }
//                    }
//                    if(movesInput.charAt(i) == 'L' || movesInput.charAt(i) == 'l'){
//                        Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
//                        if(isValid(currentPlayerPos.getRow(), currentPlayerPos.getCol() - 1)){
//                            this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow(), currentPlayerPos.getCol() - 1);
//                            counter++;
//                            System.out.println("You moved left!");
//                            bool = true;
//                        }
//                        else{
//                            this.board.printBoard();
//                            System.out.println("You made an invalid left :(");
//                            bool = false;
//                            break;
//                        }
//                    }
//                    if(movesInput.charAt(i) == ('U') || movesInput.charAt(i) == ('u')){
//                        Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
//                        if(isValid(currentPlayerPos.getRow() - 1, currentPlayerPos.getCol())){
//                            this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow() - 1, currentPlayerPos.getCol());
//                            //System.out.println(currentPlayerPos.getRow() + " " + currentPlayerPos.getCol());
//                            counter++;
//                            System.out.println("You moved up!");
//                            bool = true;
//                        }
//                        else{
//                            this.board.printBoard();
//                            System.out.println("You made an invalid up :(");
//                            bool = false;
//                            break;
//                        }
//                    }
//                    if(movesInput.charAt(i) == ('D') || movesInput.charAt(i) == ('d')){
//                        Tile currentPlayerPos = this.board.findLetter(getCurrentPlayer().getLetter());
//                        if(isValid(currentPlayerPos.getRow() + 1, currentPlayerPos.getCol())){
//                            this.board.move(currentPlayerPos.getRow(), currentPlayerPos.getCol(), currentPlayerPos.getRow() + 1, currentPlayerPos.getCol());
//                            counter++;
//                            System.out.println("You Moved down!");
//                            bool = true;
//                        }
//                        else{
//                            this.board.printBoard();
//                            System.out.println("You made an invalid down :(");
//                            bool = false;
//                            break;
//                        }
//                    }
//                    }else{
//                        System.out.println("The amount of moves you have done has exceeded the dice value" + " " + counter);
//                        bool = false;
//                        break;
//                    }
//                }
//            }
//        }

    /**
     * Ask to roll dice
     */
    public void askRoll(){
        boolean bool = false;
        while(!bool){
            System.out.println("--------------------------------------------");
            System.out.println("Are you ready to roll the dice? (Type y): ");
            String yes = input.next();
            if(yes.equalsIgnoreCase("y")){
                bool = true;
                rollDice();
            }
            else{
                System.out.println("Please type y when ready :)");
            }
        }
    }

    /**
     * Prints out the hand cards of the player who is currently playing and their character
     */
    public void printHandCards(){
        for(Player player : this.players){
            if(this.currentPlayer == player.getCharToken()){
                System.out.println("-----------------------------------");
                System.out.println("You are: " + player.getCharToken());
                System.out.println("------------------------------------");
                System.out.println("This is your hand of cards");
                for(Cards cards : player.getHand()){
                    System.out.println(cards.getDescription());
                }
            }
        }
    }

    /**
     * Deals out the cards to the players according to the number of players playing in the game
     */
//    public void dealOutCards(){
//        switch(numPeeps){
//            //if there's 3 people playing, deal out 6 cards to each player
//            case "3":
//                for(int p = 0; p < this.players.size(); p++){
//                    for(int i = 0; i < 6; i++){
//                        this.players.get(p).setHand(this.dealingOut.pop());
//                    }
//                }
//                break;
//            //if there's 4 people playing deal out 4 cards for the first two players and 5 for the remaining
//            case "4":
//                Player p1 = this.players.get(0);
//                for(int i = 0; i < 4; i++){
//                    p1.setHand(this.dealingOut.pop());
//                }
//
//                Player p2 = this.players.get(1);
//                for(int i = 0; i < 4; i++){
//                    p2.setHand(this.dealingOut.pop());
//                }
//
//                Player p3 = this.players.get(2);
//                for(int i = 0; i < 5; i++){
//                    p3.setHand(this.dealingOut.pop());
//                }
//
//                Player p4 = this.players.get(3);
//                for(int i = 0; i < 5; i++){
//                    p4.setHand(this.dealingOut.pop());
//                }
//                break;
//            //if there's 5 people playing deal out 3 cards for the first two players and 4 for the rest
//            case "5":
//                Player pp1 = this.players.get(0);
//                for(int i = 0; i < 3; i++){
//                    pp1.setHand(this.dealingOut.pop());
//                }
//
//                Player pp2 = this.players.get(1);
//                for(int i = 0; i < 3; i++){
//                    pp2.setHand(this.dealingOut.pop());
//                }
//
//                Player pp3 = this.players.get(2);
//                for(int i = 0; i < 4; i++){
//                    pp3.setHand(this.dealingOut.pop());
//                }
//
//                Player pp4 = this.players.get(3);
//                for(int i = 0; i < 4; i++){
//                    pp4.setHand(this.dealingOut.pop());
//                }
//
//                Player pp5 = this.players.get(4);
//                for(int i = 0; i < 4; i++){
//                    pp5.setHand(this.dealingOut.pop());
//                }
//                break;
//            case "6":
//                for(int p = 0; p < this.players.size(); p++){
//                    for(int i = 0; i < 3; i++){
//                        this.players.get(p).setHand(this.dealingOut.pop());
//                    }
//                }
//                break;
//        }
//
//    }

    @Override
    public void initialiseCards(){
        RoomCard room1 = new RoomCard("Kitchen", "allCards/Kitchen.png");
        RoomCard room2 = new RoomCard("Ball Room", "allCards/Ball_Room.png");
        RoomCard room3 = new RoomCard("Conservatory", "allCards/Conservatory.png");
        RoomCard room4 = new RoomCard("Billiard Room", "allCards/Billiard.png");
        RoomCard room5 = new RoomCard("Library", "allCards/Library.png");
        RoomCard room6 = new RoomCard("Study", "allCards/Study.png");
        RoomCard room7 = new RoomCard("Hall", "allCards/Hall.png");
        RoomCard room8 = new RoomCard("Lounge", "allCards/Lounge.png");
        RoomCard room9 = new RoomCard("Dining Room", "allCards/Dining.png");
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        rooms.add(room4);
        rooms.add(room5);
        rooms.add(room6);
        rooms.add(room7);
        rooms.add(room8);
        rooms.add(room9);

        WeaponCard weapon1 = new WeaponCard("Candlestick", "allCards/Candlestick.png");
        WeaponCard weapon2 = new WeaponCard("Dagger", "allCards/Dagger.png");
        WeaponCard weapon3 = new WeaponCard("Lead Pipe", "allCards/Lead-Pipe.png");
        WeaponCard weapon4 = new WeaponCard("Revolver", "allCards/Revolver.png");
        WeaponCard weapon5 = new WeaponCard("Rope", "allCards/Rope.png");
        WeaponCard weapon6 = new WeaponCard("Spanner", "allCards/Spanner.png");
        weapons.add(weapon1);
        weapons.add(weapon2);
        weapons.add(weapon3);
        weapons.add(weapon4);
        weapons.add(weapon5);
        weapons.add(weapon6);

        CharacterCard character1 = new CharacterCard("Scarlett", "allCards/Miss.Scarlett.png");
        CharacterCard character2 = new CharacterCard("Mustard", "allCards/Colonel.Mustard.png");
        CharacterCard character3 = new CharacterCard("White", "allCards/Mrs.White.png");
        CharacterCard character4 = new CharacterCard("Green", "allCards/Mr.Green.png");
        CharacterCard character5 = new CharacterCard("Peacock", "allCards/Mrs.Peacock.png");
        CharacterCard character6 = new CharacterCard("Plum", "allCards/Professor.Plum.png");
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
    public void initialiseGame(List<Player> playerList){
//        //this.board.initialise();
//        this.players.addAll(playerList);

//        //the game always starts with SCARLETT
//        this.currentPlayer = PlayableCharacters.SCARLETT;

        //game is ongoing
        this.currentState = GameState.ONGOING;

//        initialiseCards();
//        setMurderCircumstances();
//        setHandCards();
//        dealOutCards();
    }

//    /**
//     * Creates the players depending on how many players are stated
//     */
//    public List<Player> createPlayers() {
//        List<Player> playersPlaying = new ArrayList<>();
//
//        System.out.println("How many players are playing? (Min: 3, Max: 6) : ");
//
//        boolean bool = false;
//        String numPlayers;
//        while(!bool) {
//            playersPlaying.clear();
//            numPlayers = input.next();
//            this.numPeeps = numPlayers;
//            switch(numPlayers) {
//                // Valid Cases
//                case "3":
//                    //playersPlaying.clear();
//
//                    Player scarlett = new Player(PlayableCharacters.SCARLETT, 's');
//                    Player mustard = new Player(PlayableCharacters.MUSTARD, 'm');
//                    Player white = new Player(PlayableCharacters.WHITE, 'w');
//
//                    playersPlaying.add(scarlett);
//                    playersPlaying.add(mustard);
//                    playersPlaying.add(white);
//
//                    bool = true;
//                    break;
//
//                case "4":
//                    //playersPlaying.clear();
//
//                    scarlett = new Player(PlayableCharacters.SCARLETT, 's');
//                    mustard = new Player(PlayableCharacters.MUSTARD, 'm');
//                    white = new Player(PlayableCharacters.WHITE, 'w');
//                    Player green = new Player(PlayableCharacters.GREEN, 'g');
//
//                    playersPlaying.add(scarlett);
//                    playersPlaying.add(mustard);
//                    playersPlaying.add(white);
//                    playersPlaying.add(green);
//
//                    bool = true;
//                    break;
//
//                case "5":
//                    //playersPlaying.clear();
//
//                    scarlett = new Player(PlayableCharacters.SCARLETT, 's');
//                    mustard = new Player(PlayableCharacters.MUSTARD, 'm');
//                    white = new Player(PlayableCharacters.WHITE, 'w');
//                    green = new Player(PlayableCharacters.GREEN, 'g');
//                    Player peacock = new Player(PlayableCharacters.PEACOCK, 'P');
//
//                    playersPlaying.add(scarlett);
//                    playersPlaying.add(mustard);
//                    playersPlaying.add(white);
//                    playersPlaying.add(green);
//                    playersPlaying.add(peacock);
//
//                    bool = true;
//                    break;
//
//                case "6":
//                    //playersPlaying.clear();
//
//                    scarlett = new Player(PlayableCharacters.SCARLETT, 's');
//                    mustard = new Player(PlayableCharacters.MUSTARD, 'm');
//                    white = new Player(PlayableCharacters.WHITE, 'w');
//                    green = new Player(PlayableCharacters.GREEN, 'g');
//                    peacock = new Player(PlayableCharacters.PEACOCK, 'P');
//                    Player plum = new Player(PlayableCharacters.PLUM, 'p');
//
//                    playersPlaying.add(scarlett);
//                    playersPlaying.add(mustard);
//                    playersPlaying.add(white);
//                    playersPlaying.add(green);
//                    playersPlaying.add(peacock);
//                    playersPlaying.add(plum);
//
//                    bool = true;
//                    break;
//
//                default:
//                    System.out.println("--------------------------------------------------");
//                    System.out.println("String entered or invalid number of Players");
//                    System.out.println("How many players are playing? (Min: 3, Max: 6) : ");
//                    //createPlayers();
//
//                    break;
//            }
//
//        }
//        return playersPlaying;
//    }

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
        if(currentPos == null) { System.out.println("bad"); }
        while(true) {
            // we need a popup asking user to click a position on board they wanna move to // Nic and Sanj can handle this.
            //awaitingClick = true;  commented this out otherwise if player clicks every single time it will never reach the code after the while
            while(awaitingClick) {
                System.out.println("Click");
                // Should we sleep? What should we do here? This has to be a stop
                // until they click somewhere.
                // (we can talk about this on call). This method could possibly stay empty. // team needs to decide.
            }
            System.out.println("Click recieved"); // for testing purposes only
            Tile newPos = board.getTileXandY(clickedX, clickedY); // still figuring out how to calculate this from x and y of the click. // Paula and I will do this soon.
            List<Tile> moves = board.checkMove(currentPos, newPos, diceValue); // dice will be whatever variable that is holding the players rolled dice number. // We can decide who does this.


            // Now we check if the moves are valid.
            if(moves != null && newPos.getCluedoCharacter() == null) {
                invalidMove = false; //added this in for the pop up in GUI
                board.move(currentPos, moves.get(0)); // base case
                //board.redrawMethod(graph);
                redraw();

                // Now we increment through other tiles
                for(int i = 0; i < moves.size()-1; i++) {
                    board.move(moves.get(i), moves.get(i+1));
                    //board.redrawMethod(graph);
                    redraw();
                }
                newPos.getCluedoCharacter().setPosition(newPos); // the position gets changed so no need for for loops for any character.
                break;
                // if the move was valid, the loop gets broken. Otherwise it'll return to the top.
            }
            //Nic - Just adding this in to check easier if the move was invalid or not in GUI
            else{
                invalidMove = true;
                break;
            }
            // Another popup needed here saying invalid move. // Nic and Sanj can handle this.
        }
    }



//    @Override
//    protected void initialisePlayerandChar() {
//        currentCharacter = nameOfCharacters.get(0);
//        currentP = nameOfPlayers.get(0);
//    }

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



    public Player getCurrentPlayer(){
        for(Player player : this.players){
            if(this.currentPlayer == player.getCharToken()){
                return player;
            }
        }
        return null; //maybe will f up
    }
    public static void main(String[] arguments){
        //    	Board newBoard = new Board();
        //  	newBoard.readBoard();
        //      newBoard.printBoard();
        new Game(); // leave in for now until overhaul

        //These two work if new Game(); is removed or commented out. Then this class becomes the main handler for JPanel
        //JFrame frameGUI = new GUI("Cluedo");
        //frameGUI.setVisible(true);
    }
}
