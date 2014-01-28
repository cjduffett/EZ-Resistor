package com.cduffett.ez_resistor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activitysplash);
		
		Handler handler=new Handler();
        handler.postDelayed(new Runnable()
        {               
            @Override
			public void run() 
            {
            	finish();
                Intent goToMainActivity = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(goToMainActivity);                    
            }
        }, 2000);
	}
}
