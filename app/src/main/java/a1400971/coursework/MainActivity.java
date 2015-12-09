package a1400971.coursework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import a1400971.coursework.GameBox;

public class MainActivity extends Activity implements
    View.OnClickListener{
//public class MainActivity extends AppCompatActivity {

    private static final String TAG = "entryActivity";
    static int gridSize = 6;
    int startTime = -1;
    GameBox[][] level = new GameBox[6][6];

    Button startButton;
    Button leaderBoardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setContentView(R.layout.activity_main);
        setContentView(R.layout.splash);
        Log.i(TAG, "onCreate");

        // Initilising our buttons
        startButton = (Button)findViewById(R.id.startButton);
        leaderBoardButton = (Button)findViewById(R.id.leaderboardButton);

        startButton.setOnClickListener(this);
        leaderBoardButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        // Switching activity based on what button was pressed

        if(view == startButton)
        {
            Log.i(TAG, "Start Game Pressed!");
            execGame();
        }

        if(view == leaderBoardButton)
        {
            Log.i(TAG, "View Leaderboard Pressed!");
            try {
                String input = "no_name,0\n";
                String[] values = new String[10];
                String[][] parts = new String[10][];

                byte[] buffer = new byte[1024];

                // Populating our buffer
                StringBuffer fileContent = new StringBuffer("");
                int n;
                FileOutputStream fos = openFileOutput("leaderboard.txt", MODE_PRIVATE);

                for(int i = 0; i < 10; ++i)
                    fos.write(input.getBytes());
                fos.close();

                FileInputStream fis = openFileInput("leaderboard.txt");

                // Appending the content of the file to our string
                while((n = fis.read(buffer)) != -1) {
                    fileContent.append(new String(buffer, 0, n));
                }

                // Populating our arrays
                values = fileContent.toString().split("\\r?\\n", 10);

                for(int i = 0; i < 10; ++i)
                    parts[i] = values[i].split(",");

                for(int i = 0; i < 10; ++i) {
                    Log.i(TAG, parts[i][0]);
                    Log.i(TAG, parts[i][1]);
                }

                // Outputing the value of the file
                //Log.i(TAG, fileContent.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    // Changing activity
    public void execGame(){
        Log.i(TAG, "execGame called");
        Intent intent = new Intent(getApplicationContext(), Game.class);
        startActivity(intent);
    }

    public void execLeaderboard(){
        Log.i(TAG, "execLeaderboard called");
        Intent intent = new Intent(getApplicationContext(), Leaderboard.class);
        startActivity(intent);
    }
}
