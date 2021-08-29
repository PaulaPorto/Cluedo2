// 100% working as of 27/08/20
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GUI {

    /** FIELDS **/

    //the main frame of the GUI
    private JFrame frame = new JFrame("Assignment 2 - Cluedo");

    //player creation frame
    private JFrame playerCreationFrame = new JFrame("Player Creation - Name of player and the Character they are playing as");

    //menu bar for the game
    private JMenuBar menuBar;

    //???
    private JMenuBar startGameMB;

    //customise to make it a drawing pane
    private JComponent drawingPane;

    //menu FILE and GAME
    private JMenu mBFile;
    private JMenu mBGame;

    //the menu stuff inside the game
    private JMenuItem mIStartGame;

    //panels for the player creation window
    private JPanel textFieldPanel = new JPanel();
    private JPanel scarlettPanel = new JPanel();
    private JPanel mustardPanel = new JPanel();
    private JPanel whitePanel = new JPanel();
    private JPanel greenPanel = new JPanel();
    private JPanel peacockPanel = new JPanel();
    private JPanel plumPanel = new JPanel();

    //label for the text field in the player creation frame
    private JLabel textFieldLabel = new JLabel("Name of Player: ");

    //text field to allow each player to enter their name
    private JTextField textField1 = new JTextField(50);

    //Radio buttons for each of the players
    private JRadioButton rBScarlett = new JRadioButton("Scarlett");
    private JRadioButton rBMustard = new JRadioButton("Mustard");
    private JRadioButton rBWhite = new JRadioButton("White");
    private JRadioButton rBGreen = new JRadioButton("Green");
    private JRadioButton rBPeacock = new JRadioButton("Peacock");
    private JRadioButton rBPlum = new JRadioButton("Plum");

    private JButton confirmButton = new JButton("Confirm");

    //the labels needed for the dice (two dices are displayed to the player)
    private JLabel dice1 = new JLabel();
    private JLabel dice2 = new JLabel();

    //array of the dice icons to switch to depending on the dice roll
    private final ImageIcon[] diceImages = {
            new ImageIcon("images/Dice1.png"),
            new ImageIcon("images/Dice2.png"),
            new ImageIcon("images/Dice3.png"),
            new ImageIcon("images/Dice4.png"),
            new ImageIcon("images/Dice5.png"),
            new ImageIcon("images/Dice6.png")
    };

    private final ImageIcon rollDiceImage = new ImageIcon("images/rollDice.png");

    //the labels needed for the dice (two dices are displayed to the player)
    private JLabel card1 = new JLabel();
    private JLabel card2 = new JLabel();
    private JLabel card3 = new JLabel();
    private JLabel card4 = new JLabel();
    private JLabel card5 = new JLabel();
    private JLabel card6 = new JLabel();


    //booleans just for checking that the current char and current players are not null
    private boolean currentCharacterNull = true;
    private boolean currentPlayerNull = true;

    //player creation dialog
    private JDialog playerCreationDialog = new JDialog();

    /**
     * Is called when the drawing area is redrawn and performs all the logic for
     * the actual drawing, which is done with the passed Graphics object
     * @param g graphics object
     */
    protected abstract void redraw(Graphics g);

    /**
     * Is called when something is clicked on the drawing pane (???)
     * @param e the mouse event
     */
    protected abstract void onClick(MouseEvent e);

    /**
     * Is called when the roll dice button is pressed,
     * rolls the dice and returns the two values that is rolled
     * @return  List of integers that contains the two values of the dices
     */
    protected abstract List<Integer> rollDice();

    /**
     * Is called when the board is clicked and checks the move and does the
     * moving of the character itself.
     */
    protected abstract void doMove();

    /**
     * Is called when the game starts, creates ALL of the cards in the game
     * (6 Character cards, 6 Weapon cards, 9 Room cards)
     */
    protected abstract void initialiseCards();

    /**
     * Is called when the game starts, picks 3 random cards (Character, Weapon, Room)
     * and places them in the "sleeve"
     */
    protected abstract void setMurderCircumstances();

    /**
     * Is called when the confirm button is pressed in the character and player creation screen,
     * combines all the different cards, puts them in one list and then gets shuffled
     */
    protected abstract void setHandCards();

    /**
     * Is called when the game starts to create the cards and deal them out to each of the players
     * that are playing.
     */
    protected abstract void dealCards();

    /**
     * Is called to order the players and characters in the right order that
     * they will be playing in.
     */
    protected abstract void orderPlayersAndCharacters();

    /**
     * Is called when the end turn button is pressed, ends the turn of the current player
     * and goes to the next player.
     */
    protected abstract void endTurn();

    /**
     * Redraws the board
     */
    public void redraw(){
        frame.repaint();
    }

    /**
     * Called when the current player in play changes,
     * this changes the image, the name of the current player and the name of the current character
     * @param character     JLabel that shows the current character's image
     * @param playerName    JLabel that shows the current player's name
     * @param characterName     JLabel that shows the current character's name
     */
    public void setCurrentPlayerAndCharacterPanel(JLabel character, JLabel playerName, JLabel characterName){
        if(Game.currentCharacter.equalsIgnoreCase("Miss Scarlett")){
            character.setIcon(new ImageIcon("Scarlet.png"));
        }
        else if(Game.currentCharacter.equalsIgnoreCase("Col. Mustard")){
            character.setIcon(new ImageIcon("Mustard.png"));
        }
        else if(Game.currentCharacter.equalsIgnoreCase("Mrs. White")){
            character.setIcon(new ImageIcon("White.png"));
        }
        else if(Game.currentCharacter.equalsIgnoreCase("Mr. Green")){
            character.setIcon(new ImageIcon("Green.png"));
        }
        else if(Game.currentCharacter.equalsIgnoreCase("Mrs. Peacock")){
            character.setIcon(new ImageIcon("Peacock.png"));
        }
        else if(Game.currentCharacter.equalsIgnoreCase("Prof. Plum")){
            character.setIcon(new ImageIcon("Plum.png"));
        }
        playerName.setText(Game.currentP);
        characterName.setText(Game.currentCharacter);
    }

    /**
     * Updates the images of the cards depending on what cards the current player has
     * @param card1     JLabel that will contain a card
     * @param card2     JLabel that will contain a card
     * @param card3     JLabel that will contain a card
     * @param card4     JLabel that will contain a card
     * @param card5     JLabel that will contain a card
     * @param card6     JLabel that will contain a card
     */
    public void setCardsPanels(JLabel card1, JLabel card2, JLabel card3, JLabel card4, JLabel card5, JLabel card6){
        if(Game.totalPlaying == 3){
            for(int i = 0; i < Board.characterList.size(); i++){
                if(Game.currentCharacter.equalsIgnoreCase(Board.characterList.get(i).getName())){
                    card1.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(0).getImageFile()));
                    card2.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(1).getImageFile()));
                    card3.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(2).getImageFile()));
                    card4.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(3).getImageFile()));
                    card5.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(4).getImageFile()));
                    card6.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(5).getImageFile()));
                }
            }
        }
        else if(Game.totalPlaying == 4){
            for(int i = 0; i < Board.characterList.size(); i++){
                if(Game.currentCharacter.equalsIgnoreCase(Board.characterList.get(i).getName())){
                    if(i == 0 || i == 1){
                        card1.setIcon(null);
                        card2.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(0).getImageFile()));
                        card3.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(1).getImageFile()));
                        card4.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(2).getImageFile()));
                        card5.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(3).getImageFile()));
                    }
                    else{
                        card1.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(0).getImageFile()));
                        card2.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(1).getImageFile()));
                        card3.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(2).getImageFile()));
                        card4.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(3).getImageFile()));
                        card5.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(4).getImageFile()));
                    }
                }
            }
        }
        else if(Game.totalPlaying == 5){
            for(int i = 0; i < Board.characterList.size(); i++){
                if(Game.currentCharacter.equalsIgnoreCase(Board.characterList.get(i).getName())){
                    if(i == 0 || i == 1){
                        card2.setIcon(null);
                        card3.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(0).getImageFile()));
                        card4.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(1).getImageFile()));
                        card5.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(2).getImageFile()));
                    }
                    else{
                        card2.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(0).getImageFile()));
                        card3.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(1).getImageFile()));
                        card4.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(2).getImageFile()));
                        card5.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(3).getImageFile()));
                    }
                }
            }
        }
        else if(Game.totalPlaying == 6){
            for(int i = 0; i < Board.characterList.size(); i++){
                if(Game.currentCharacter.equalsIgnoreCase(Board.characterList.get(i).getName())){
                    card3.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(0).getImageFile()));
                    card4.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(1).getImageFile()));
                    card5.setIcon(new ImageIcon(Board.characterList.get(i).getHandCards().get(2).getImageFile()));
                }
            }
        }
    }

    /** CONSTRUCTOR **/
    public GUI(){
        initialise();
    }

    @SuppressWarnings("serial")
    private void initialise() {
        this.menuBar = new JMenuBar();

        //idk what file is going to contain lmao
        this.mBFile = new JMenu("FILE");
        this.menuBar.add(this.mBFile);

        //JMenu for the GAME tab in the menubar and adding it to the menuBar
        this.mBGame = new JMenu("START");
        this.menuBar.add(this.mBGame);

        //getting a JMenuItem to show up when you click on the GAME on the menu bar
        this.mIStartGame = new JMenuItem("Start Game");
        mIStartGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        this.mBGame.add(this.mIStartGame);

        //the setup for the player creation frame that pops up when the "Start Game" button is pressed is pressed in the GAME menu
        this.textFieldPanel.setLayout(new FlowLayout());
        this.scarlettPanel.setLayout(new FlowLayout());
        this.mustardPanel.setLayout(new FlowLayout());
        this.whitePanel.setLayout(new FlowLayout());
        this.greenPanel.setLayout(new FlowLayout());
        this.peacockPanel.setLayout(new FlowLayout());
        this.plumPanel.setLayout(new FlowLayout());

        this.textFieldPanel.add(this.textFieldLabel);
        this.textFieldPanel.add(this.textField1);
        this.scarlettPanel.add(this.rBScarlett);
        this.mustardPanel.add(this.rBMustard);
        this.whitePanel.add(this.rBWhite);
        this.greenPanel.add(this.rBGreen);
        this.peacockPanel.add(this.rBPeacock);
        this.plumPanel.add(this.rBPlum);

        ButtonGroup rBGroup = new ButtonGroup();
        rBGroup.add(this.rBScarlett);
        rBGroup.add(this.rBMustard);
        rBGroup.add(this.rBWhite);
        rBGroup.add(this.rBGreen);
        rBGroup.add(this.rBPeacock);
        rBGroup.add(this.rBPlum);

//        playerCreationFrame.setLayout(new GridLayout(7, 1, 5, 5));
//        playerCreationFrame.add(this.textFieldPanel);
//        playerCreationFrame.add(this.scarlettPanel);
//        playerCreationFrame.add(this.mustardPanel);
//        playerCreationFrame.add(this.whitePanel);
//        playerCreationFrame.add(this.greenPanel);
//        playerCreationFrame.add(this.peacockPanel);
//        playerCreationFrame.add(this.plumPanel);
//        playerCreationFrame.pack();
//        playerCreationFrame.setLocationRelativeTo(null);

        //adding an action listener to the "Start Game" to make a dialog box pop up for the player creation
        this.mIStartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                playerCreationFrame.setVisible(true);
                playerCreationDialog.setLayout(new GridLayout(8, 1, 5, 5));
                playerCreationDialog.add(textFieldPanel);
                playerCreationDialog.add(scarlettPanel);
                playerCreationDialog.add(mustardPanel);
                playerCreationDialog.add(whitePanel);
                playerCreationDialog.add(greenPanel);
                playerCreationDialog.add(peacockPanel);
                playerCreationDialog.add(plumPanel);
                playerCreationDialog.add(confirmButton);
                playerCreationDialog.pack();
                playerCreationDialog.setLocationRelativeTo(null);
                playerCreationDialog.setVisible(true);
                redraw();
                initialiseCards();
                setMurderCircumstances();
                setHandCards();
            }
        });

        //action listener for the text field in the player creation to detect the name of the player
        this.textField1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField1.getText();
                System.out.println(text);
                textField1.setText("");
                if(currentPlayerNull){
                    Game.currentP = text;
                }
                currentPlayerNull = false;
                Game.nameOfPlayers.add(text);
            }
        });

        //action listener for the radio button for scarlett
        this.rBScarlett.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rBScarlett.setEnabled(false);
                if(currentCharacterNull){
                    Game.currentCharacter = "Miss Scarlett";
                }
                currentCharacterNull = false;
                Game.nameOfCharacters.add("Miss Scarlett");
            }
        });

        //action listener for the radio button for mustard
        this.rBMustard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rBMustard.setEnabled(false);
                if(currentCharacterNull){
                    Game.currentCharacter = "Col. Mustard";
                }
                currentCharacterNull = false;
                Game.nameOfCharacters.add("Col. Mustard");
            }
        });

        //action listener for the radio button for white
        this.rBWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rBWhite.setEnabled(false);
                if(currentCharacterNull){
                    Game.currentCharacter = "Mrs. White";
                }
                currentCharacterNull = false;
                Game.nameOfCharacters.add("Mrs. White");
            }
        });

        //action listener for the radio button for green
        this.rBGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rBGreen.setEnabled(false);
                if(currentCharacterNull){
                    Game.currentCharacter = "Mr. Green";
                }
                currentCharacterNull = false;
                Game.nameOfCharacters.add("Mr. Green");
            }
        });

        //action listener for the radio button for peacock
        this.rBPeacock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rBPeacock.setEnabled(false);
                if(currentCharacterNull){
                    Game.currentCharacter = "Mrs. Peacock";
                }
                currentCharacterNull = false;
                Game.nameOfCharacters.add("Mrs. Peacock");
            }
        });

        //action listener for the radio button for plum
        this.rBPlum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rBPlum.setEnabled(false);
                if(currentCharacterNull){
                    Game.currentCharacter = "Prof. Plum";
                }
                currentCharacterNull = false;
                Game.nameOfCharacters.add("Prof. Plum");
            }
        });

        //the bottom panel
        JPanel bottomPanel = new JPanel();
        Border edge = BorderFactory.createEmptyBorder(0, 0, 0, 0);
        bottomPanel.setBorder(edge);
        this.frame.setLayout(new GridBagLayout());
        this.frame.setSize(890, 755);

        //constraints for the bottomPanel
        GridBagConstraints bottomConstraints = new GridBagConstraints();
        bottomConstraints.fill = GridBagConstraints.HORIZONTAL;
        bottomConstraints.ipady = 130;
        bottomConstraints.weightx = 100.0;
        bottomConstraints.weighty = 1.0;
        bottomConstraints.anchor = GridBagConstraints.PAGE_END;
        bottomConstraints.insets = new Insets(10, 0, 0, 0);
        bottomConstraints.gridwidth = 3;
        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 1;


        //Where the character is going to be placed on the right side of the window
        JPanel characterPane = new JPanel();
        GridBagConstraints characterConstraints = new GridBagConstraints();
        characterConstraints.fill = GridBagConstraints.VERTICAL;
        characterConstraints.ipady = 50;
        characterConstraints.weightx = 50;
        characterConstraints.weighty = 100;
        characterConstraints.anchor = GridBagConstraints.LINE_END;
        characterConstraints.insets = new Insets(40, 0, 310, 150);
        characterConstraints.gridwidth = 3;
        characterConstraints.gridx = 0;
        characterConstraints.gridy = 1;
        characterPane.setBackground(Color.LIGHT_GRAY);

        //Panel for the top panel where the board is placed
        GridBagConstraints topConstraints = new GridBagConstraints();
        topConstraints.fill = GridBagConstraints.BOTH;
        topConstraints.ipady = 50;
        topConstraints.weightx = 50;
        topConstraints.weighty = 100;
        topConstraints.anchor = GridBagConstraints.CENTER;
        topConstraints.insets = new Insets(7, 55, 220, 0);
        topConstraints.gridwidth = 3;
        topConstraints.gridx = 0;
        topConstraints.gridy = 1;

        //Panel for the black background at the back at the top behind the board and character displayed
        JPanel background = new JPanel();
        GridBagConstraints backConstraints = new GridBagConstraints();
        backConstraints.fill = GridBagConstraints.BOTH;
        backConstraints.ipady = 50;
        backConstraints.weightx = 50;
        backConstraints.weighty = 100;
        backConstraints.anchor = GridBagConstraints.CENTER;
        backConstraints.insets = new Insets(0, 0, 220, 0);
        backConstraints.gridwidth = 3;
        backConstraints.gridx = 0;
        backConstraints.gridy = 1;
        background.setBackground(Color.DARK_GRAY);


        //drawing pane stuff, making the drawing canvas overriding the paintComponent method toi draw whatever we like
        this.drawingPane = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                redraw(g);
            }
        };
        this.drawingPane.setPreferredSize(new Dimension(400, 400));
        this.drawingPane.setVisible(true);

        //can change this to something else idk just added cause idk what to do lol
        this.drawingPane.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                onClick(e); //
                JDialog invalidMove = new JDialog();
                JLabel invalid = new JLabel("You have made an invalid move, please try again");
                Game.awaitingClick = false;
                doMove();
                if(Game.invalidMove){
                    invalidMove.setLayout(new FlowLayout());
                    invalidMove.add(invalid);
                    invalidMove.pack();
                    invalidMove.setLocationRelativeTo(null);
                    invalidMove.setVisible(true);
                }
            }
        });


        //same as the top one, can implement to make it have scrolling or something
        this.drawingPane.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                //this is just for zooming in
            }
        });

        JLabel character = new JLabel("", SwingConstants.CENTER);
        JLabel currentPlayer = new JLabel("Current Player: ", SwingConstants.CENTER);
        JLabel currentPlayerName = new JLabel("", SwingConstants.CENTER);
        JLabel currentCharacter = new JLabel("Current Character: ", SwingConstants.CENTER);
        JLabel currentCharacterName = new JLabel("", SwingConstants.CENTER);

        Font font = new Font("Courier", Font.BOLD, 12);
        currentPlayer.setFont(font);
        currentCharacter.setFont(font);

        characterPane.setLayout(new GridLayout(5,0, 0, 0));
        //action listener for the confirm button
        this.confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                orderPlayersAndCharacters();
                setCurrentPlayerAndCharacterPanel(character, currentPlayerName, currentCharacterName);
                dealCards();
                setCardsPanels(card1, card2, card3, card4, card5, card6);
                //characterPane.setVisible(true);
                playerCreationDialog.dispose();
            }
        });

        //bottom panel, adding things to the bottom panel such as the dice, the buttons and the cards
        bottomPanel.setLayout(new GridLayout(0, 9, 5, 5));
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        //adding the dice images
        dice1.setIcon(rollDiceImage);
        dice2.setIcon(rollDiceImage);

        bottomPanel.add(dice1);
        bottomPanel.add(dice2);

        //adding the buttons for rolling the dice, suggesting and accusing
        JPanel buttonPanel = new JPanel();
        JButton rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.setToolTipText("Click to roll the dice");

        JButton suggestButton = new JButton("Suggest");
        suggestButton.setToolTipText("Click to make a suggestion");

        JButton accuseButton = new JButton("Accuse");
        accuseButton.setToolTipText("Click to make an accusation");

        JButton endTurnButton = new JButton("End Turn");
        endTurnButton.setToolTipText("Click to let the next player make a move");

        buttonPanel.setLayout(new GridLayout(4, 0, 0, 0));

        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(rollDiceButton);
        buttonPanel.add(suggestButton);
        buttonPanel.add(accuseButton);
        buttonPanel.add(endTurnButton);
        bottomPanel.add(buttonPanel);

        // TODO: 29/08/2020 CARDS
        //adding the card images
        bottomPanel.add(card1);
        bottomPanel.add(card2);
        bottomPanel.add(card3);
        bottomPanel.add(card4);
        bottomPanel.add(card5);
        bottomPanel.add(card6);

        //action listener for the rolling dice
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //example for the dice rolling and the showing of the number on the screen based on the
                //initialisePlayerandChar();
                List<Integer> rolled = rollDice();

                int total = rolled.get(0) + rolled.get(1);

                JDialog rolledDialog = new JDialog();
                JLabel totalRolled = new JLabel("You rolled " + total + "!");
                // TODO: 25/08/2020 Add an extra message to say that they should click on the middle of the tile on the drawing of the board
                JLabel canMoveInfo = new JLabel(" You can now click on the board according to the your dice value");
                //rolledDialog.setSize(100, 80);
                rolledDialog.setLayout(new FlowLayout());
                rolledDialog.add(totalRolled);
                rolledDialog.add(canMoveInfo);
                rolledDialog.pack();
                rolledDialog.setLocationRelativeTo(null);
                rolledDialog.setVisible(true);

                dice1.setIcon(diceImages[rolled.get(0) - 1]);
                dice2.setIcon(diceImages[rolled.get(1) - 1]);

            }
        });

        //action listener for suggesting
        suggestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //action listener for accusing
        accuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //action listener for ending turn
        endTurnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTurn();
                dice1.setIcon(rollDiceImage);
                dice2.setIcon(rollDiceImage);
                setCurrentPlayerAndCharacterPanel(character, currentPlayerName, currentCharacterName);
                setCardsPanels(card1, card2, card3, card4, card5, card6);
            }
        });

        // TODO: 22/08/2020 clean up for later
        characterPane.add(character);
        characterPane.add(currentPlayer);
        characterPane.add(currentPlayerName);
        characterPane.add(currentCharacter);
        characterPane.add(currentCharacterName);


        this.frame.add(characterPane, characterConstraints);
        this.drawingPane.setBackground(Color.BLUE);
        this.frame.add(drawingPane, topConstraints);
        this.frame.add(bottomPanel, bottomConstraints);
        this.frame.setJMenuBar(this.menuBar);
        this.frame.add(background, backConstraints);
        //needs to not be resizable currently as this would ruin the look of the thing as we haven't figured it out
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(frame,
                        "Do you want to Exit ?", "Exit Confirmation : ",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (result == JOptionPane.NO_OPTION)
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });

        // frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
