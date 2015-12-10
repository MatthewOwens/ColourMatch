package a1400971.coursework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Leaderboard extends Activity implements
        View.OnClickListener{

    private String TAG = "leaderboardActivity";
    private static int numStoredScores = 5;
    String[] names = new String[numStoredScores];
    String[] scores = new String[numStoredScores];
    Button retryButton;
    Button titleButton;
    long completetionTime = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Getting the level completion time
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            completetionTime = extras.getLong("COMPLETION_TIME", -1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.leaderboard);
        Log.i(TAG, "onCreate");

        // Initilising the buttons
        retryButton = (Button)findViewById(R.id.retryButton);
        titleButton = (Button)findViewById(R.id.titleButton);

        // Setting the listeners
        retryButton.setOnClickListener(this);
        titleButton.setOnClickListener(this);
     }

    @Override
    public void onClick(View view){
        if(view == retryButton){
            execGame();
        }

        if(view == titleButton){
            execTitle();
        }
    }

    private void execGame(){
        Log.i(TAG, "execGame called!");
        Intent intent = new Intent(getApplicationContext(), Game.class);
        startActivity(intent);
    }

    private void execTitle(){
        Log.i(TAG, "execTitle called!");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
