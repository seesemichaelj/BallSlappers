package com.cen3031.android.packages.ballslappers;

import java.io.IOException;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class SinglePlayerCreateActivity extends Activity {
	
	
	private boolean powerupsen = false;
	private String difficultySelected = "easy";
	private int numberofcpu;
	private int lives = 1;
	private int tempLives = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		SharedPreferences prefs = HomeScreenActivity.settings;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_create);
                
	     Spinner difficulty = (Spinner) findViewById(R.id.DifficultySelect);
	     // Create an ArrayAdapter using the string array and a default spinner layout
	     ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	    		 R.array.Difficulties, android.R.layout.simple_spinner_item);
	     // Specify the layout to use when the list of choices appears
	     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     // Apply the adapter to the spinner
	     difficulty.setAdapter(adapter);
	     
	     
	     difficulty.setOnItemSelectedListener(new OnItemSelectedListener() {
	    	 public void onItemSelected(AdapterView<?> arg0, View view, int pos, long id) {
    		 	switch(pos){
		 			case 0:
		 				difficultySelected = "easy";
		 				break;
		 			case 1:
		 				difficultySelected = "medium";
		 				break;
		 			case 2:
		 				difficultySelected = "hard";
		 				break;
		 			default:
		 				break;
		 		}
			 } 
	    	 
	    	 public void onNothingSelected(AdapterView<?> arg0) {

	         }
	     });
	     
	     Spinner NUMCPU = (Spinner) findViewById(R.id.CPUSelect);
	     // Create an ArrayAdapter using the string array and a default spinner layout
	     ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
	    		 R.array.NUMCPU, android.R.layout.simple_spinner_item);
	     // Specify the layout to use when the list of choices appears
	     adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     // Apply the adapter to the spinner
	     NUMCPU.setAdapter(adapter2);
	     
	     NUMCPU.setOnItemSelectedListener(new OnItemSelectedListener() {
	    	 public void onItemSelected(AdapterView<?> arg0, View view, int pos, long id) {
    		 	switch(pos){
	    		 	case 0:
	    				numberofcpu=1;
	    				break;
	    			case 1:
	    				numberofcpu=2;
	    				break;
	    			case 2:
	    				numberofcpu=3;
	    				break;
	    			default:
	    				break;
	    		}
    		 	
			 } 
	    	 
	    	 public void onNothingSelected(AdapterView<?> arg0) {

	         }
	     });
	     
	     Log.i("onCreate()", "singlePlayerCreateActivity");
		 difficulty.setSelection(prefs.getInt("difficulty", 0));
		 ((EditText) findViewById(R.id.Edit_Lives_Text)).setText(prefs.getInt("numberLives", 1)+"");
		 NUMCPU.setSelection(prefs.getInt("cpunumber", 1) - 1);
		 ((EditText) findViewById(R.id.GameName)).setText(prefs.getString("userName", null));
         powerupsen = prefs.getBoolean("powerupsen", false);
         ((CheckBox) findViewById(R.id.enablePowerUps)).setChecked(powerupsen);
    }
    
	@Override
    public void onResume(){
    	if (HomeScreenActivity.SOUND_ENABLED == true)
			HomeScreenActivity.mediaPlayer.start();
    	
    	super.onResume();
    	ToggleButton tb = (ToggleButton) findViewById(R.id.toggleSound);
        tb.setChecked(HomeScreenActivity.SOUND_ENABLED);
    }
    
    @Override
    public void onPause() {
    	if (HomeScreenActivity.mediaPlayer.isPlaying())
    		HomeScreenActivity.mediaPlayer.pause();
    	
    	super.onPause();
    }
		
	public void minusOneLife(View view){
     	EditText editText = (EditText) findViewById(R.id.Edit_Lives_Text);
     	tempLives = Integer.parseInt(editText.getText().toString());
     	if(tempLives == 1){
     		tempLives = 100;
     	}
     	lives = tempLives - 1;
     	editText.setText(String.valueOf(lives));
    }
	
	public void plusOneLife(View view){
     	EditText editText = (EditText) findViewById(R.id.Edit_Lives_Text);
     	tempLives = Integer.parseInt(editText.getText().toString());
     	if(tempLives == 99){
     		tempLives = 0;
     	}
     	lives = tempLives + 1;
     	editText.setText(String.valueOf(lives));
    }
    
    public void PlaySinglePlayer(View view){
    	HomeScreenActivity.mediaPlayer.stop();
    	HomeScreenActivity.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.fightsong100);
    	HomeScreenActivity.mediaPlayer.setLooping(true);
    	
    	if(HomeScreenActivity.SOUND_ENABLED)
    		HomeScreenActivity.mediaPlayer.start();
    	
     	EditText editText = (EditText) findViewById(R.id.Edit_Lives_Text);
     	lives = Integer.parseInt(editText.getText().toString());
     	
     	EditText gameName = (EditText) findViewById(R.id.GameName);
     	String name = gameName.getText().toString();
     	

    	SharedPreferences.Editor e = HomeScreenActivity.settings.edit();
    	e.putInt("difficulty", ((Spinner) findViewById(R.id.DifficultySelect)).getSelectedItemPosition());
    	e.putInt("numberLives", lives);
    	e.putInt("cpunumber", numberofcpu);
    	e.putString("userName", name);
        e.putBoolean("powerupsen", powerupsen);
    	e.commit();
     	
    	Bundle bundle = new Bundle();
    	bundle.putString("difficulty", difficultySelected);
    	bundle.putInt("numberLives", lives);
    	bundle.putInt("cpunumber", numberofcpu);
    	bundle.putString("userName", name);
        bundle.putBoolean("powerups", powerupsen);
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtras(bundle);
    	startActivity(intent);//, bundle);
    }
    
    public void poweruptoggle(View view) {
    	powerupsen = !powerupsen;
    }
    
    public void toggleMusic(View view){
    	if(HomeScreenActivity.SOUND_ENABLED) {
    		HomeScreenActivity.mediaPlayer.pause();
		}
    	else
    	{
    		HomeScreenActivity.mediaPlayer.start();
    	}
    	
    	HomeScreenActivity.SOUND_ENABLED = !HomeScreenActivity.SOUND_ENABLED;
    	SharedPreferences.Editor e = HomeScreenActivity.settings.edit();
    	e.putBoolean("sound_enabled", HomeScreenActivity.SOUND_ENABLED);
    	e.commit();
    }
}
