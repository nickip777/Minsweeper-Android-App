package com.example.nick.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HelpScreen extends AppCompatActivity {

    public static final String TAG = "Help Screen Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_screen);
        setText();
    }

    //METHODS FOR ACTION BAR************************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help_screen_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back_button:
                Log.d(TAG, "User Clicked Back (Action Bar)");
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent makeIntent(Context context){
        return new Intent(context,HelpScreen.class);
    }

    //set text in help screen
    public void setText(){
        TextView textViewAboutAuthor = (TextView) findViewById(R.id.textViewAboutDescription);
        TextView textViewObjective = (TextView) findViewById(R.id.textViewObjectiveDescription);
        TextView textViewCitations = (TextView) findViewById(R.id.textViewCitationsDescription);
        textViewObjective.setText("OH NO! Terrorists have set up a bomb in the village, we must help diffuse it! Click on the blocks to find where the bombs are. " +
                "If it is not a block, a number will appear representing the number of bombs on the same row and column. " +
                "Solve the puzzle and help save the world. Don't let the terrorists win!");
        textViewAboutAuthor.setText("This game was created by Corey See and Nick Ip");
        textViewCitations.setText("bomb.png - http://images.akamai.steamusercontent.com/ugc/388794500592713059/9EA2813117BD7543913F0F582AFC94765D08E4F8/?interpolation=lanczos-none&output-format=jpeg&output-quality=95&fit=inside|128:128&composite-to%3D%2A%2C%2A%7C128%3A128&background-color=black\n\n" +
        "game_background.png - https://www.johnsto.co.uk/i/design/making-dust2/cscz_01.jpg\n\n" +
        "help_screen_background.png - http://files.gamebanana.com/img/ss/guis/52c78b9331102.jpg\n\n" +
        "main_background - http://csdownload.playnet.lt/wp-content/uploads/2013/12/cs-download-counter-strike-setup-logo-banner.jpg\n\n" +
        "options_screen_background.png - http://hd.wallpaperswide.com/thumbs/light_background-t2.jpg\n\n" +
        "beep.mp3 - http://gamebanana.com/sounds/35255\n\n" +
        "go.mp3 - http://gamebanana.com/sounds/35309\n\n" + "help_screen_background.jpg - http://files.gamebanana.com/img/ss/textures/4e7f4933762e1.jpg\n\n" +
        "options_screen_background.jpg - http://files.gamebanana.com/img/ss/maps/54920a0b6fa09.jpg\n\n" + "defuser.jpg - http://vignette4.wikia.nocookie.net/cswikia/images/8/8d/Defuserhud_csgo.png/revision/latest?cb=20150528225453\n\n");
    }
}
