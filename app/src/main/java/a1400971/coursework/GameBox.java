package a1400971.coursework;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

/**
 * Class to describe a coloured box in our game.
 */
public class GameBox {
    public enum Colour {BLANK, RED, YELLOW, GREEN, ORANGE, PURPLE, BLUE}

    private Colour colour;
    //private int gridScale;
    private Button button;
    int x;
    int y;

    public GameBox(int gridX, int gridY, Button foundButton) {
        // Setting the colour to be blank initially
        colour = Colour.BLANK;
        button = foundButton;
        //button.setBackgroundColor(Color.WHITE);

        x = gridX;
        y = gridY;
    }

    // Default constructor
    public GameBox(Context context){
        button = new Button(context);
    }

    public void setColour(Colour newColour){
        colour = newColour;

        switch (newColour) {
            default:
                button.setBackgroundColor(Color.rgb(45, 45, 45));
                break;
            case BLANK:
                //button.setBackgroundColor(Color.rgb(45, 45, 45));
                button.setBackgroundColor(Color.rgb(255, 255, 255));
                break;
            case RED:
                button.setBackgroundColor(Color.rgb(179, 80, 80));
                break;
            case GREEN:
                button.setBackgroundColor(Color.rgb(142, 196, 137));
                break;
            case YELLOW:
                button.setBackgroundColor(Color.rgb(237, 237, 100));
                break;
            case ORANGE:
                button.setBackgroundColor(Color.rgb(183, 92, 43));
                break;
            case PURPLE:
                button.setBackgroundColor(Color.rgb(132, 37, 141));
                break;
            case BLUE:
                button.setBackgroundColor(Color.rgb(97, 119, 141));
                break;
        }
    }

    public Colour getColour(){
        return colour;
    }

    public Button getButton(){
        return button;
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

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
