package a1400971.coursework;

import android.app.Activity;
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

import a1400971.coursework.GameBox;

public class MainActivity extends Activity implements
    View.OnClickListener{
//public class MainActivity extends AppCompatActivity {

    private static final String TAG = "entryActivity";
    static int gridSize = 6;
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
        }

        if(view == leaderBoardButton)
        {
            Log.i(TAG, "View Leaderboard Pressed!");
        }
    }
}
