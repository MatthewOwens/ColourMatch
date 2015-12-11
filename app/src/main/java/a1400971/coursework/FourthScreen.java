package a1400971.coursework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FourthScreen extends Activity implements
        View.OnClickListener{

        Button fleeButton;

        @Override
        protected void onCreate(Bundle savedInstanceBundle){
                super.onCreate(savedInstanceBundle);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);

                //setContentView(R.layout.activity_main);
                setContentView(R.layout.fourthscreen);

                fleeButton = (Button)findViewById(R.id.turnBackButton);
                fleeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
                if(view == fleeButton) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                }
        }
}
