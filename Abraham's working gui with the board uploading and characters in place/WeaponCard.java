/**
 * WeaponCard that represents the 6 weapons in the game
 * @authors antigonich singhsanj2 romcypaul alfredabra
 */
public class WeaponCard extends Cards
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

    public WeaponCard(String weaponName, String imageFile)
    {
        this.description = weaponName;
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
    public String getImageFile() {
        return imageFile;
    }

    @Override
    public String toString()
    {
        return "This card is " + this.description;
    }
}
