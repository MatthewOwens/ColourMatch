package a1400971.coursework;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Calendar;
import java.util.Random;

/*import a1400971.coursework.GameBox;*/

/**
 * This class is used to describe the main mechanics of the game, and is it's own activity.
 *
 * Executed from the entry point activity, when the Game ends the player is offered a choice of
 * either restarting the game or proceeding to the leaderboard.
 */
public class Game extends Activity implements
        View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Log.i(TAG, "Game onCreate called!");
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prevClicked = new GameBox(getApplicationContext());
        int[] colourCounts = new int[6];
        startTime = calendar.getTimeInMillis();
        Random rand = new Random();

        // Initilising our gameboxes

        for(int i = 0; i < levelSize; ++i) {
            for(int j = 0; j < levelSize; ++j){
                String buttonString = "button" + ((i * levelSize) + j + 1);
                Log.i(TAG, buttonString);
                //int buttonID = getResources().getIdentifier("button" +  (((i * levelSize)  + j) + 1), "id", getPackageName());
                int buttonID = getResources().getIdentifier(buttonString, "id", getPackageName());
                level[i][j] = new GameBox(i, j, (Button)findViewById(buttonID));
                level[i][j].getButton().setOnClickListener(this);

                // Setting a random colour
                int colour = -1;

                do {
                    colour = rand.nextInt(levelSize) + 1;
                } while(colourCounts[colour - 1] >= levelSize);

                Log.i(TAG, "Creating gamebox with colour " + colour);
                colourCounts[colour - 1]++;
                level[i][j].setColour(GameBox.Colour.values()[colour]);
            }
        }
    }

    @Override
    public void onClick(View view){
        Log.i(TAG, "onClick");

        // Check what button we've clicked
        for(int i = 0; i < levelSize; ++i){
            for(int j = 0; j < levelSize; ++j){
                if(view == level[i][j].getButton()){
                    Log.i(TAG, "button " + i + "," + j + " pressed");

                    // Don't do anything if the button is blank
                    if(level[i][j].getColour() != GameBox.Colour.BLANK){

                        // If we've clicked a valid tile
                        if(level[i][j] != prevClicked && level[i][j].compareColour(prevClicked)){

                            // Changing the colour of each tile to blank
                            level[i][j].setColour(GameBox.Colour.BLANK);
                            level[prevClicked.getX()][prevClicked.getY()].setColour(GameBox.Colour.BLANK);
                        }
                    }

                    prevClicked = level[i][j];

                    // No need to continue
                    break;
                }
            }
        }

        // Checking if all the tiles have been cleared
        boolean complete = true;
        for(int i = 0; i < levelSize; ++i){
            for(int j = 0; j < levelSize; ++j){
                if(level[i][j].getColour() != GameBox.Colour.BLANK){
                    complete = false;
                    break;
                }
            }
        }

        if(complete)
        {
            Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
            long currentTime = calendar.getTimeInMillis();
            //long completionTime = calendar.getTimeInMillis() - startTime;

            // No idea why completionTime is always zero, could be an emulation issue
            long completionTime  = currentTime - startTime;

            // Bumping up the completionTime by one to test scoring properly
            completionTime++;
            intent.putExtra("COMPLETION_TIME", completionTime);

            Log.i(TAG, "Start time: " + startTime);
            Log.i(TAG, "Curr. time: " + currentTime);
            Log.i(TAG, "Comp. time: " + completionTime);
            startActivity(intent);
        }
    }
    private String TAG = "gameActivity";
    private static int levelSize = 6;
    private GameBox[][] level = new GameBox[levelSize][levelSize];
    private GameBox prevClicked;
    private Calendar calendar = Calendar.getInstance();
    private long startTime;
}
