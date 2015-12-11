package a1400971.coursework;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class Leaderboard extends Activity implements
        View.OnClickListener{

    private String TAG = "leaderboardActivity";
    private static int numStoredScores = 5;

    String[] names = new String[numStoredScores];
    String[] scores = new String[numStoredScores];
    TextView[] nameViews = new TextView[numStoredScores];
    TextView[] scoreViews = new TextView[numStoredScores];

    Button retryButton;
    Button titleButton;
    long completionTime = -1;
    boolean fileReadComplete = false;
    boolean workerCompleted = false;

    Worker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Getting the level completion time
        Bundle extras = getIntent().getExtras();
        if(extras != null)
            completionTime = extras.getLong("COMPLETION_TIME", -1);

        Log.i(TAG, "Got a completion time of " + completionTime + " ms");

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

        // Defaulting the text fields
        for(int i = 0; i < numStoredScores; ++i) {
            String nameIdString = "nameView" + (i + 1);
            String scoreIdString = "scoreView" + (i + 1);

            int nameID = getResources().getIdentifier(nameIdString, "id", getPackageName());
            int scoreID = getResources().getIdentifier(scoreIdString, "id", getPackageName());

            nameViews[i] = (TextView)findViewById(nameID);
            scoreViews[i] = (TextView)findViewById(scoreID);

            nameViews[i].setText(nameIdString);
            scoreViews[i].setText(scoreIdString);
        }

        // Starting our file stuff, if we haven't entered from the title
        worker = new Worker();
        worker.execute();
        try {
            worker.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view){
        if(workerCompleted) {
            if (view == retryButton) {
                execGame();
            }

            if (view == titleButton) {
                execTitle();
            }
        }
    }

    @Override
    public void onStop(){
        super.onStop();

        Log.i(TAG, "onStop");
        // Writing out our scores
        FileOutputStream fos;

        try {
            fos = openFileOutput("Leaderboard.txt", MODE_PRIVATE);
            String line;

            for(int i = 0; i < numStoredScores; ++i) {
                // Adding deliminator for split() and newline for reading in another time
                line = names[i] + "," + scores[i] + "\n";

                fos.write(line.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
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


    //static class Worker extends AsyncTask<Void, Void, ArrayList< ArrayList<String> >{
    class Worker extends AsyncTask<Void, Void, Void> {
        String fileName = "Leaderboard.txt";

        @Override
        protected Void doInBackground(Void... Params) {
            BufferedReader in;

            try {
                in = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
                String line = in.readLine();
                int lineNumber = 0;

                while (line != null && lineNumber < numStoredScores) {
                    String[] parts = line.split(",");

                    // Parsing this line
                    names[lineNumber] = parts[0];
                    scores[lineNumber] = parts[1];

                    if(names[lineNumber] == "null" || names[lineNumber] == null) {
                        names[lineNumber] = "no_name";
                        scores[lineNumber] = "0";
                    }
                    lineNumber++;
                }
            } catch (IOException e) {
                e.printStackTrace();

                // Ensuring "null" isn't written to the score portion of the string
                // Will wipe scores though
                for(int i = 0; i < numStoredScores; ++i) {
                    names[i] = "no_name";
                    scores[i] = "0";
                }

            }

            fileReadComplete = true;
            return null;
        }

        @Override
        // Back to the UI thread
        protected void onPostExecute(Void param) {
            int i;
            // Since we can assume the scores are ordered, check from the back
            //for (i = numStoredScores - 1; i >= 0; --i) {
            for(i = 0; i < numStoredScores; ++i){
                if(scores[i] != "null") {   // Avoiding horrible crashes
                    if (Long.valueOf(scores[i]).longValue() < completionTime) {
                        break;
                    }
                }
                else{
                    if(0 < completionTime)
                        break;
                }
            }

            if (i != numStoredScores) {
                // There was a highscore!, write it to the table
                scores[i] = Long.toString(completionTime);
                names[i] = "Player";    // Not getting user input because I forgot to use
                // edit fields in the layout!
            }

            // Updating the fields
            for (int j = 0; j < numStoredScores; ++j) {
                nameViews[j].setText(names[j]);
                scoreViews[j].setText(scores[j]);
            }

            Log.i(TAG, "workerThread ended desu!");
            workerCompleted = true;
        }
    }
}
