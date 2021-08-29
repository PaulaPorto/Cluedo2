/**
 * RoomCard that represents the 9 room cards in the game
 * @authors antigonich singhsanj2 romcypaul alfredabra
 */
public class RoomCard extends Cards
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //the description of the card
    private String description;
    private String imageFile;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public RoomCard(String roomName, String imageFile)
    {
        this.description = roomName;
        this.imageFile = imageFile;
    }

    //------------------------
    // INTERFACE
    //------------------------

    @Override
    public String getDescription()
    {
        return description;
    }

    @Override
    public String getImageFile(){
        return imageFile;
    }

    @Override
    public String toString()
    {
        return "This card is " + this.description;
    }
}
