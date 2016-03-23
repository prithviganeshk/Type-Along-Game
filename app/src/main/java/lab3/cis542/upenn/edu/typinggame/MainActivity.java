package lab3.cis542.upenn.edu.typinggame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import java.util.Random;


@SuppressWarnings("ALL")
public class MainActivity extends Activity implements TextWatcher{

    TextView textView;
    Button Submit_button;
    Button Quit_button;
    EditText typedText;
    private static final int READY_DIALOG = 1;
    private static final int CORRECT_DIALOG = 2;
    private static final int INCORRECT_DIALOG = 3;
    private static final int DIFFICULTY_DIALOG = 4;
    private long startTime;
    private long endTime;
    public static float bestTime = 10000000;
    public static float worsTime = 10000000;
    public static int score;
    public static int current_score = 0;
    private static float easy_time = 10000000;
    private static float hard_time = 10000000;
    private static float medium_time = 10000000;
    private static float easy_wtime = 10000000;
    private static float hard_wtime = 10000000;
    private static float medium_wtime = 10000000;
    public float displayTime;
    public long happystart;
    public long happyend;
    public float happyseconds;
    public boolean besttime = false;
    public boolean newuser = false;
    //private Context context;
    public static String difficulty = null;
    private int highestscore =0;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Time = "timeKey";
    public static final String Score = "scoreKey";
    private TypingGame typingGame;
    final Context contxt = this;
    public int factor =0;
    public static SharedPreferences sharedpreferences;
    public static boolean firsttime =false;
    public static boolean worsttime = false;
    Editor editor;
    ImageView happy;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        typingGame = new TypingGame();
        happy = (ImageView)findViewById(R.id.happyimage);
        typedText = (EditText) findViewById(R.id.Text2Type); //using instance to get id of typed text frame
        happystart = System.currentTimeMillis();
        typedText.addTextChangedListener(this);



        /* show dialog asking if ready or not */
       // textView.setText("not working");
        showDialog(READY_DIALOG);
        showDialog(DIFFICULTY_DIALOG);


        final MediaPlayer mediaplayer = MediaPlayer.create(MainActivity.this,R.raw.tenacity);
        mediaplayer.start();
        Submit_button = (Button) findViewById(R.id.SubmitButton); //on clicking SUBMIT button
        Submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                String typedtext = typedText.getText().toString(); //putting the typed text into a string
                String displayedtext = textView.getText().toString();

                if (typedtext.equals(displayedtext)) { //change text to green color later
                    removeDialog(CORRECT_DIALOG);
                    showDialog(CORRECT_DIALOG);
                }
                else {//change this text to red color later
                    removeDialog(INCORRECT_DIALOG);
                    showDialog(INCORRECT_DIALOG);
                }
            }
        });


        Quit_button = (Button) findViewById(R.id.QuitButton);
        Quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaplayer.stop();
                finish();
            }
        });

    }


    protected Dialog onCreateDialog(int id) { //creating dialog box

        if (id == DIFFICULTY_DIALOG) { // To ask if ready / Start the game
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Choose Level of Difficutly"); // this is the message to display
            builder.setPositiveButton("Easy", new DialogInterface.OnClickListener() { // this is the method to call when the button is clicked
                public void onClick(DialogInterface dialog, int id) { //once the dialog appears
                    difficulty = "easy";
                    String text = sentenceGen(difficulty);
                    textView = (TextView) findViewById(R.id.line_to_type);
                    textView.setText(text); //need to set the sentence which has to be typed through java
                    dialog.cancel();
                }
            });
            builder.setNeutralButton("Medium", new DialogInterface.OnClickListener() { // this is the method to call when the button is clicked
                public void onClick(DialogInterface dialog, int id) { //once the dialog appears
                    difficulty = "medium";
                    String text = sentenceGen(difficulty);
                    textView = (TextView) findViewById(R.id.line_to_type);
                    textView.setText(text); //need to set the sentence which has to be typed through java
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("Hard", new DialogInterface.OnClickListener() { // this is the method to call when the button is clicked
                public void onClick(DialogInterface dialog, int id) { //once the dialog appears
                    difficulty = "hard";
                    String text = sentenceGen(difficulty);
                    textView = (TextView) findViewById(R.id.line_to_type);
                    textView.setText(text); //need to set the sentence which has to be typed through java
                    dialog.cancel();
                }
            });


            return builder.create();
        }


        if (id == READY_DIALOG) { // To ask if ready / Start the game
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are You Ready?"); // this is the message to display
            builder.setPositiveButton(R.string.yeah, new DialogInterface.OnClickListener() { // this is the method to call when the button is clicked
                public void onClick(DialogInterface dialog, int id) { //once the dialog appears
                    dialog.cancel();
                    startTime = System.currentTimeMillis(); //start the time
                }
            });
            return builder.create();
        }

        if (id == CORRECT_DIALOG) { //If submitted corrected answer

            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);

            endTime = System.currentTimeMillis(); //time at instant of ending

            long time = endTime - startTime; //time diff i.e. time take to type
            updateScores(typingGame.Name, difficulty, score, bestTime);
            firsttime = false;
            displayTime = (time / 100) / (float) 10.0;
            if (bestTime == 10000000) {

                bestTime = displayTime;
                worsTime = displayTime;
                //Toast.makeText(context, "That's Right! You finished in " + displayTime + "seconds", Toast.LENGTH_LONG).show(); //display right and time if string matches
                builder2.setMessage("That's Right! You finished in " + displayTime + " seconds!.Your score is " + score + " \n              Click Yes to Play Again. "); // display right and time if string matches
                builder2.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // this is the method to call when the button is clicked
                    public void onClick(DialogInterface dialog, int id) { //once the dialog appears
                        dialog.cancel();
                        startTime = System.currentTimeMillis(); //start the time
                    }
                });
            } else if (displayTime <= bestTime) { //best time [finished in shortest time]
                besttime = true;
                bestTime = displayTime;
                // Toast.makeText(context, "That's Right! You finished in " + displayTime + "seconds. This is your best time", Toast.LENGTH_LONG).show(); //display right and time if string matches
                builder2.setMessage("That's Right! You finished in " + displayTime + " seconds." +
                        "                   THIS IS YOUR BEST TIME! Your score is " + score + " \n" + "                  Click Yes to Play Again. "); // display right and time if string matches
                builder2.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // this is the method to call when the button is clicked
                    public void onClick(DialogInterface dialog, int id) { //once the dialog appears
                        dialog.cancel();
                        startTime = System.currentTimeMillis(); //start the time
                    }
                });
            } else {
                if(displayTime>worsTime) {
                    worsTime = displayTime;
                    worsttime=true;
                }
                else worsttime = false;
                besttime = false;
                builder2.setMessage("That's Right! You finished in " + displayTime + " seconds.  This best time so far is " + bestTime + " seconds. Your score is " + score + " \n" + "               Click Yes to Play Again."); // display right and time if string matches
                builder2.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // this is the method to call when the button is clicked
                    public void onClick(DialogInterface dialog, int id) { //once the dialog appears
                        dialog.cancel();
                        startTime = System.currentTimeMillis(); //start the time
                    }

                });
            }
                typedText.setText("");

                addScores(typingGame.Name, difficulty, bestTime);
                String text = sentenceGen(difficulty);
                //happystart = System.currentTimeMillis();
                textView.setText(text); //need to set the sentence which has to be typed through java

                return builder2.create();
            }

            if (id == INCORRECT_DIALOG) {
                // Toast.makeText(context, "That's not Right...Try Again", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setMessage("That's not Right...Try Again"); // display right and time if string matches
                builder3.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { // this is the method to call when the button is clicked
                    public void onClick(DialogInterface dialog, int id) { //once the dialog appears
                        dialog.cancel();
                    }
                });
                return builder3.create();
            } else return null;
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        public String sentenceGen(String level) {
         Random generator = new Random();

         String sentence = null;
         StringBuilder buffer = new StringBuilder();


         if(level == "easy") {
             String article[] = {"the ", "a ", "one ", "some ", "any "};
             String noun[] = {"boy ", "dog ", "car ", "bicycle ", "wolf ", "dragon "};
             String verb[] = {"ran ", "jumped ", "flew ", "moved "};
             String preposition[] = {"away ", "towards ", "around ", "near "};
             String nouns[] = {"girl", "cat", "bus", "scooter", "penguin", "puppet"};

             int article1 = generator.nextInt(article.length - 1);
             int noun1 = generator.nextInt(article.length - 1);
             int verb1 = generator.nextInt(article.length - 1);
             int preposition1 = generator.nextInt(article.length - 1);
             int article2 = generator.nextInt(article.length - 1);
             int nouns1 = generator.nextInt(article.length - 1);

             buffer.append(article[article1]).append(noun[noun1]).append(verb[verb1]).append(preposition[preposition1]).append(article[article2]).append(nouns[nouns1]).append(".");
             buffer.setCharAt(0, Character.toUpperCase(buffer.charAt(0)));
             sentence = buffer.toString();
             current_score = sentence.length();
             return sentence;

         }

           else if(level == "medium") {
                String article[] = {"the precarious ", "an ambitious ", "a wonderful ", "a diligent ", "a mindful "};
                String noun[] = {"dragon ", "rinosaurus ", "kangaroo ", "bicycle ", "pokemon ", "dragon "};
                String verb[] = {"ran ", "jumped ", "flew ", "moved "};
                String preposition[] = {"away from ", "towards ", "upon ", "around "};
                String nouns[] = {"beautiful girl", "scary cat", "gigantic bus", "stupid scooter", "happy penguin", "worthless puppet"};

             int article1 = generator.nextInt(article.length - 1);
             int noun1 = generator.nextInt(article.length - 1);
             int verb1 = generator.nextInt(article.length - 1);
             int preposition1 = generator.nextInt(article.length - 1);
             int article2 = generator.nextInt(article.length - 1);
             int nouns1 = generator.nextInt(article.length - 1);

             buffer.append(article[article1]).append(noun[noun1]).append(verb[verb1]).append(preposition[preposition1]).append(article[article2]).append(nouns[nouns1]).append(".");
             buffer.setCharAt(0, Character.toUpperCase(buffer.charAt(0)));
             sentence = buffer.toString();
             current_score = sentence.length();
             return sentence;


         }

            else {
             String article[] = {"the most beautiful ", "the best and elegant ", "all of the ", "almost all of ", "awfully lot of the "};
             String noun[] = {"dragons ", "rinosauruses ", "kangaroos ", "bicycle tires ", "crocodile hunters ", "dragon hunters "};
             String verb[] = {"ran hastily ", "jumped ", "few ", "moved "};
             String preposition[] = {"away from ", "towards ", "upon ", "around "};
             String nouns[] = {"beautiful girl", "scary cat", "gigantic bus", "stupid scooter", "happy penguin", "worthless puppet"};

             int article1 = generator.nextInt(article.length - 1);
             int noun1 = generator.nextInt(article.length - 1);
             int verb1 = generator.nextInt(article.length - 1);
             int preposition1 = generator.nextInt(article.length - 1);
             int article2 = generator.nextInt(article.length - 1);
             int nouns1 = generator.nextInt(article.length - 1);

             buffer.append(article[article1]).append(noun[noun1]).append(verb[verb1]).append(preposition[preposition1]).append(article[article2]).append(nouns[nouns1]).append(".");
             buffer.setCharAt(0, Character.toUpperCase(buffer.charAt(0)));
             sentence = buffer.toString();
             current_score = sentence.length();
             return sentence;


         }

     }

   public void updateScores(String User_name, String Difficulty, int userscore, float bestime){


        if (sharedpreferences.contains(User_name)&& firsttime){
            score = sharedpreferences.getInt(User_name+"score",score);

            if(score ==0){
                newuser=true;
                score = current_score;
            }

           //
            if(Difficulty.equals("easy")&& firsttime ){
                bestTime = sharedpreferences.getFloat("easyTime",easy_time);

               // bestTime = easy_time;
            }

            else if (Difficulty.equals("medium")&& firsttime){
                bestTime = sharedpreferences.getFloat("mediumTime",medium_time);

               // bestTime = medium_time;
            }

            else{
                if (Difficulty.equals("hard")&& firsttime) {
                    bestTime = sharedpreferences.getFloat("hardTime", hard_time);

                    //bestTime = hard_time;
                }
            }

            if(Difficulty.equals("easy")&& firsttime){
               // if(!newuser)
                worsTime = sharedpreferences.getFloat("easyTime",easy_wtime);
                // bestTime = easy_time;
            }

            else if (Difficulty.equals("medium")&& firsttime){
               // if(!newuser)

                worsTime = sharedpreferences.getFloat("mediumTime",medium_wtime);
                // bestTime = medium_time;
            }

            else{
                if (firsttime) {
                    //if(!newuser)
                    worsTime = sharedpreferences.getFloat("hardTime", hard_wtime);
                    //bestTime = hard_time;
                }
            }
            newuser=false;
        }

        else{
            if(newuser) {
                bestTime = 10000000;
                worsTime = 10000000;
                score = current_score;
                newuser = false;
            }
        }
    }

    public void addScores(String User_name, String Difficulty, float bestime){

        score += current_score;

        if(highestscore==0){
            highestscore = score;
        }

        if(score > highestscore ){
            highestscore = score;
        }



        if(Difficulty.equals("easy") && besttime){

        easy_time = bestTime;
        editor.putFloat("easyTime",easy_time);
        editor.putString("easybestuser",User_name);
        }

        else if (Difficulty.equals("medium") && besttime){

        medium_time = bestTime;
        editor.putFloat("mediumTime",medium_time);
        editor.putString("mediumbestuser",User_name);
        }

        else {
            if (besttime) {
                hard_time = bestTime;
                editor.putFloat("hardTime", hard_time);
              //  editor.putString("hardbestuser",User_name);
            }
        }

        if(Difficulty.equals("easy") && worsttime){

          easy_wtime = worsTime;
          editor.putFloat("easywTime",easy_wtime);
          //  editor.putString("easyworstuser",User_name);
        }

        else if (Difficulty.equals("medium") && worsttime){

            medium_wtime = worsTime;
            editor.putFloat("mediumwTime",medium_wtime);
          //  editor.putString("mediumworstuser",User_name);
        }

        else {
            if (worsttime) {
                hard_wtime = worsTime;
                editor.putFloat("hardwTime", hard_wtime);
                //editor.putString("hardworstuser",User_name);
            }
        }

        editor.putBoolean(User_name, true);
        editor.putInt(User_name + "score", score);
       // editor.putFloat(User_name + Difficulty + "time", bestTime);
        editor.putInt("highestscore",highestscore);

        editor.commit();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       //Toast.makeText(this, "working", Toast.LENGTH_SHORT).show();
       // happyend = System.currentTimeMillis();
       // long happytime = happystart - happyend; //time diff i.e. time take to type
       // happyseconds = (happytime / 100) / (float) 10.0;
        if (count < 3) {
            happy.setImageResource(R.drawable.happy);
        } else if (count >=4 && count < 10) {
            happy.setImageResource(R.drawable.straight);
        }
        else{
            happy.setImageResource(R.drawable.sad);
        }
    }
    @Override
    public void afterTextChanged(Editable s) {

    }
}