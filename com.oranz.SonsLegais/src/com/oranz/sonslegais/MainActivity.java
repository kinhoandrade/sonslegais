package com.oranz.sonslegais;

import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	private TextView logoSonsLegais;
	  ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	  private SimpleAdapter listaAdapter;
	  private SoundPool soundPool;
	  private HashMap<Integer, Integer> soundsMap;
	  int SOUND1=1;
	  int SOUND2=2;
	  int SOUND3=3;

    @SuppressLint("UseSparseArrays")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        logoSonsLegais = (TextView) findViewById(R.id.logoTextView);
        
        listaAdapter = new SimpleAdapter( 
 				this, 
 				list,
 				R.layout.listlayout,
 				new String[] { "sound","origin" },
 				new int[] { R.id.text1, R.id.text2 }  );
        
        setListAdapter(listaAdapter);
        
        addItem("Não entendi o que ele falou","Panico na TV");
        addItem("Hum Boiola","Panico na TV");
        addItem("Hoje Sim, Hoje Não","Cleber Machado");
        
        Typeface sounds = Typeface.createFromAsset(this.getAssets(), "fonts/sounds1.ttf");
        logoSonsLegais.setTypeface(sounds);

        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);

        soundsMap = new HashMap<Integer, Integer>();
        soundsMap.put(SOUND1, soundPool.load(this, R.raw.humboiola, 1));
        soundsMap.put(SOUND2, soundPool.load(this, R.raw.hojesimhojenao, 1));
        soundsMap.put(SOUND3, soundPool.load(this, R.raw.naoentendioqueelefalou, 1));

		soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	    soundPool.load(getApplicationContext(), R.raw.humboiola, 1);
	    soundPool.load(getApplicationContext(), R.raw.hojesimhojenao, 1);
	    soundPool.load(getApplicationContext(), R.raw.naoentendioqueelefalou, 1);
        
        getListView().setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				String selected = ""+list.get(position).get("sound");
				Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
			    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
			    float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			    float volume = maxVolume; 
			    
			    if(selected.equalsIgnoreCase("Hum Boiola")){
			    		soundPool.play(soundsMap.get(1), volume, volume, 1, 0, 1f);
				}else if(selected.equalsIgnoreCase("Hoje Sim, Hoje Não")){
		    		soundPool.play(soundsMap.get(2), volume, volume, 1, 0, 1f);
				}else if(selected.equalsIgnoreCase("Não entendi o que ele falou")){
		    		soundPool.play(soundsMap.get(3), volume, volume, 1, 0, 1f);
				}
			}
          });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void addItem(String first, String second) {
  	  HashMap<String,String> item = new HashMap<String,String>();
  	  item.put( "sound", first );
  	  item.put( "origin", second);
  	  list.add( item );
      listaAdapter.notifyDataSetChanged();
  	}
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
      
    }
}
