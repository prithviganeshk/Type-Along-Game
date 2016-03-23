package lab3.cis542.upenn.edu.typinggame;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class TypingGame extends Activity {

    Button Login_Button;
    Button Cancel_button;
    Button Quit_button;
    Button Signup_button;
    Button logout_button;
    Button scores_button;
    EditText name;
    EditText newuser;
    TextView loggedin_user;
    TextView usernamedisplay;
    final Context context = this;
    ArrayList<String> users = new ArrayList<String>();
    public static String Name;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        logout_button = (Button)findViewById(R.id.logout_button);
        logout_button.setVisibility(Button.INVISIBLE);
        loggedin_user = (TextView)findViewById((R.id.loggedin_user));
        loggedin_user.setVisibility(TextView.INVISIBLE);
        mainActivity = new MainActivity();
        final MediaPlayer mediaplayer = MediaPlayer.create(TypingGame.this,R.raw.tenacity
        );
        mediaplayer.start();


        newuser = (EditText) findViewById(R.id.new_user);
        name =(EditText)findViewById(R.id.UserTypeText);
        usernamedisplay = (TextView)findViewById(R.id.UserNamedisplay);
        Login_Button = (Button)findViewById(R.id.login_button);
        Cancel_button = (Button)findViewById(R.id.cancel_button);
        Quit_button = (Button)findViewById(R.id.quit_button);
        Signup_button =(Button)findViewById(R.id.singup_button);
        scores_button = (Button)findViewById(R.id.scores_button);
        scores_button.setVisibility(TextView.INVISIBLE);


        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag =1;


                Name = name.getText().toString();

                if (sharedpreferences.contains(Name)) {
                        mediaplayer.stop();

                        flag = 0;
                        Signup_button.setVisibility(Button.INVISIBLE);
                        Cancel_button.setVisibility(Button.INVISIBLE);
                        Login_Button.setVisibility(Button.INVISIBLE);
                        newuser.setVisibility(EditText.INVISIBLE);
                        name.setVisibility(EditText.INVISIBLE);
                        usernamedisplay.setVisibility(TextView.INVISIBLE);
                        scores_button.setVisibility(TextView.VISIBLE);
                        logout_button.setVisibility(Button.VISIBLE);
                        loggedin_user.setText(Name + " LOGGED IN ");
                        loggedin_user.setVisibility(TextView.VISIBLE);
                        mainActivity.firsttime = true;
                        mainActivity.score=0;
                        Intent intent = new Intent(TypingGame.this, MainActivity.class);
                        startActivity(intent);

                }

                    if(flag ==1) {
                        final Toast toast = Toast.makeText(context, "Invalid UserName", Toast.LENGTH_SHORT);
                        toast.show();
                    }

            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Signup_button.setVisibility(Button.VISIBLE);
                Cancel_button.setVisibility(Button.VISIBLE);
                Login_Button.setVisibility(Button.VISIBLE);
                newuser.setVisibility(EditText.VISIBLE);
                name.setVisibility(EditText.VISIBLE);
                usernamedisplay.setVisibility(TextView.VISIBLE);
                logout_button.setVisibility(Button.INVISIBLE);
                loggedin_user.setVisibility(TextView.INVISIBLE);
                scores_button.setVisibility(TextView.INVISIBLE);
                mainActivity.bestTime = 10000000;
                mainActivity.score=0;

            }
        });

        Signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String New_Name = newuser.getText().toString();
                if(New_Name != null) {
                    addNames(New_Name);
                    final Toast toast = Toast.makeText(context, "New user " + New_Name + " added", Toast.LENGTH_SHORT);
                    newuser.setText("New User?\n Double Tap here to sign up");
                    toast.show();
                }

            }
        });

        newuser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            newuser.setText("");
            }
        });

        Quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaplayer.stop();
                finish();
            }
        });

        scores_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TypingGame.this, Scores.class);
                startActivity(intent2);
            }
        });

        Cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loginin, menu);
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

    public void addNames(String User_name){
        editor.putBoolean(User_name,true);
        editor.commit();
    }
}
