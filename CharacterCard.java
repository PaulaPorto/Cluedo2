/**
 *  CharacterCard class that represent the CharacterCards, which are the 6 characters.
 *  @authors antigonich singhsanj2 romcypaul alfredabra
 */
public class CharacterCard extends Cards
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //the description of the card
    private String description;

    //the image name of the card
    private String imageFile;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public CharacterCard(String characterName, String imageFile)
    {
        this.description = characterName;
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
