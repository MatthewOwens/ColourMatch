package a1400971.coursework;

/**
 * Created by Matthew on 03/12/15.
 *
 * Class to describe a coloured box in our game.
 */
public class GameBox {
    public enum Colour {BLANK, RED, YELLOW, GREEN, ORANGE, PURPLE, BLUE}

    private Colour colour;
    private int gridX;
    private int gridY;
    //private int gridScale;

    public GameBox(int x, int y) {
        gridX = x;
        gridY = y;

        // Setting the colour to be blank initially
        colour = Colour.BLANK;
    }

    public void setColour(Colour newColour){
        colour = newColour;
    }

    public Colour getColour(){
        return colour;
    }

    public boolean compareColour(Colour otherColour){
        // Since blank is an invalid colour for comparison, just return false
        if(colour == Colour.BLANK || otherColour == Colour.BLANK)
            return false;
        else return (otherColour == colour);
    }

    public boolean compareColour(GameBox otherBox){
        return compareColour(otherBox.getColour());
    }
}
