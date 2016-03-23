package lab3.cis542.upenn.edu.typinggame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Scores extends Activity {

    TextView scores;
    TextView easy_Username;
    TextView med_Username;
    TextView hard_Username;

    TextView easy_Bestime;
    TextView med_Bestime;
    TextView hard_Bestime;

    TextView easy_Worstime;
    TextView med_Worstime;
    TextView hard_Worstime;

    MainActivity mainActivity;
    TypingGame typingGame;
    Button back;
    int score;
    float easy_time;
    float medium_time;
    float hard_time;
    float easy_wotime;
    float medium_wotime;
    float hard_wotime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        mainActivity = new MainActivity();
        typingGame = new TypingGame();

        scores = (TextView)findViewById(R.id.score_textView);

        easy_Username= (TextView)findViewById(R.id.easy_username);
        med_Username= (TextView)findViewById(R.id.med_username);
        hard_Username= (TextView)findViewById(R.id.hard_username);

        easy_Bestime= (TextView)findViewById(R.id.easy_bestime);
        med_Bestime= (TextView)findViewById(R.id.med_bestime);
        hard_Bestime= (TextView)findViewById(R.id.hard_bestime);

        easy_Worstime= (TextView)findViewById(R.id.easy_worsttime);
        med_Worstime= (TextView)findViewById(R.id.med_worstime);
        hard_Worstime= (TextView)findViewById(R.id.hard_worstime);



        back = (Button)findViewById(R.id.back_button);

        String username = typingGame.Name;

        score = mainActivity.sharedpreferences.getInt(username+"score",score);
        scores.setText(Integer.toString(score));

        float easy_btime = MainActivity.sharedpreferences.getFloat("easyTime", easy_time);
        easy_Bestime.setText(Float.toString(easy_btime));
        float medium_btime = MainActivity.sharedpreferences.getFloat("mediumTime", medium_time);
        med_Bestime.setText(Float.toString(medium_btime));
        float hard_btime = MainActivity.sharedpreferences.getFloat("hardTime", hard_time);
        hard_Bestime.setText(Float.toString(hard_btime));

        float easy_wtime = MainActivity.sharedpreferences.getFloat("easywTime", easy_wotime);
        easy_Worstime.setText(Float.toString(easy_wtime));
        float medium_wtime = MainActivity.sharedpreferences.getFloat("mediumwTime", medium_wotime);
        med_Worstime.setText(Float.toString(medium_wtime));
        float hard_wtime = MainActivity.sharedpreferences.getFloat("hardwTime", hard_wotime);
        hard_Worstime.setText(Float.toString(hard_wtime));

        //if(mainActivity.difficulty.equals("easy")){
            String easyuser =mainActivity.sharedpreferences.getString("easybestuser","");
            if(easyuser != null)
            easy_Username.setText(easyuser);
       // }
       // else if(mainActivity.difficulty.equals("medium")){
            String meduser = mainActivity.sharedpreferences.getString("mediumbestuser","");
            if(meduser != null)
             med_Username.setText(meduser);
       // }
      //  else {
            String haruser = mainActivity.sharedpreferences.getString("hardbestuser","");
             if(haruser != null)
             hard_Username.setText(haruser);
      //  }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
