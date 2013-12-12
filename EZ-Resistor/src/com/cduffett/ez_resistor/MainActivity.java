package com.cduffett.ez_resistor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	Button menu_button_R2Cbutton;
	Button menu_button_C2Rbutton;
	Button menu_button_EQRbutton;
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    MenuItem item= menu.findItem(R.id.action_settings);
	    item.setVisible(false);
	    super.onPrepareOptionsMenu(menu);
	    return true;
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitymain);
		
		menu_button_R2Cbutton = (Button) findViewById(R.id.menu_button_R2Cbutton);
		menu_button_C2Rbutton = (Button) findViewById(R.id.menu_button_C2Rbutton);
		menu_button_EQRbutton = (Button) findViewById(R.id.menu_button_EQRbutton);
	
		menu_button_R2Cbutton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) {
				Intent goToR2C = new Intent(arg0.getContext(), R2CActivity.class);
				startActivity(goToR2C);			
			}
		});
	
		menu_button_C2Rbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent goToC2R = new Intent(arg0.getContext(), C2RActivity.class);
				startActivity(goToC2R);			
			}
		});
	
		menu_button_EQRbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent goToEQR = new Intent(arg0.getContext(), EQRActivity.class);
				startActivity(goToEQR);			
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
