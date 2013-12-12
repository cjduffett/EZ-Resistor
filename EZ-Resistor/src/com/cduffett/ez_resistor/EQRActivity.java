package com.cduffett.ez_resistor;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EQRActivity extends Activity {

	// Display the res
	TextView result;
	
	// Button
	Button parallelSelect;
	Button seriesSelect;
	Button calculateButton;
	Button resetButton;
	
	// Drawable
	Drawable parallelSelectDrawable;
	Drawable seriesSelectDrawable;
	
	// Image of parallel and series circuit
	ImageView parallelImage;
	ImageView seriesImage;
	
	// Edit Text
	EditText resistance1;
	EditText resistance2;
	
	// Unit selection drop down menu
	Spinner baseDropMenu1;
	Spinner baseDropMenu2;
	
	EQR EQR_Calculator;
	boolean isParallel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activityeqr);
		// @NOTE: Initial loading of the activity begins here
		initializeVariables();
		// Initialize Spinners
		addItemsOnSpinner1();
		addItemsOnSpinner2();
		
		// Set default selection to parallel
		isParallel = true;
		parallelSelectDrawable.setColorFilter(0xffc7bebe, PorterDuff.Mode.MULTIPLY);
		seriesImage.setVisibility(View.INVISIBLE);
		
		// Parallel Circuit is selected
		parallelSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				isParallel = true;
				parallelSelectDrawable.setColorFilter(0xffc7bebe, PorterDuff.Mode.MULTIPLY);
				parallelImage.setVisibility(View.VISIBLE);
				seriesSelectDrawable.setColorFilter(0xffffffff, PorterDuff.Mode.MULTIPLY);
				seriesImage.setVisibility(View.INVISIBLE);
			}
		});
		// Series Circuit is selected
		seriesSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				isParallel = false;
				parallelSelectDrawable.setColorFilter(0xffffffff, PorterDuff.Mode.MULTIPLY);
				parallelImage.setVisibility(View.INVISIBLE);
				seriesSelectDrawable.setColorFilter(0xffc7bebe, PorterDuff.Mode.MULTIPLY);
				seriesImage.setVisibility(View.VISIBLE);
			}
		});
		
		calculateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				if (isParallel)
				{
					if (EQR_Calculator.parallelCalculate(getResistance1Value(), getResistance2Value(),
							baseIntToChar1(), baseIntToChar2()))
					{
						result.setText(EQR_Calculator.getResult());
					}
					else
					{
						Toast.makeText(getApplicationContext(), EQR_Calculator.getErrorMessage(),
								Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					if (EQR_Calculator.seriesCalculate(getResistance1Value(), getResistance2Value(), 
							baseIntToChar1(), baseIntToChar2()))
					{
						result.setText(EQR_Calculator.getResult());
					}
					else
					{
						Toast.makeText(getApplicationContext(), EQR_Calculator.getErrorMessage(),
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0)
			{
				baseDropMenu1.setSelection(0);
				baseDropMenu2.setSelection(0);
				resistance1.setText("");
				resistance2.setText("");
				result.setText("Result");
			}
		});
		
	}
	
	//======================================================================

	@SuppressWarnings("deprecation")
	private void initializeVariables() {
		
		
		result = (TextView) findViewById(R.id.EQR_result);
		
		// Button
		parallelSelect = (Button) findViewById(R.id.EQR_button_parallel);
		seriesSelect = (Button) findViewById(R.id.EQR_button_series);
		calculateButton = (Button) findViewById(R.id.EQR_button_calculate);
		resetButton = (Button) findViewById(R.id.EQR_button_reset);
		
		// Drawable
		parallelSelectDrawable = getResources().getDrawable(R.drawable.eqrparallel);
		seriesSelectDrawable = getResources().getDrawable(R.drawable.eqrseries);
		// Detect for android os version
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
		{
			parallelSelect.setBackgroundDrawable(parallelSelectDrawable);
			seriesSelect.setBackgroundDrawable(seriesSelectDrawable);
		}
		else
		{
			parallelSelect.setBackground(parallelSelectDrawable);
			seriesSelect.setBackground(seriesSelectDrawable);
		}
		
		// Image of parallel and series circuit
		parallelImage = (ImageView) findViewById(R.id.EQR_parallel_circuit);
		seriesImage = (ImageView) findViewById(R.id.EQR_series_circuit);
		
		// Edit Text
		resistance1 = (EditText) findViewById(R.id.EQR_textfield_r1input);
		resistance2 = (EditText) findViewById(R.id.EQR_textfield_r2input);
		
		// Unit selection drop down menu
		baseDropMenu1 = (Spinner) findViewById(R.id.EQR_dropmenu_r1ohms);
		baseDropMenu2 = (Spinner) findViewById(R.id.EQR_dropmenu_r2ohms);
		
		EQR_Calculator = new EQR();
		isParallel = true;
	}

	private void addItemsOnSpinner1() {
		List<String> list = new ArrayList<String>();
		list.add("Ω");
		list.add("kΩ");
		list.add("MΩ");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		baseDropMenu1.setAdapter(dataAdapter);
		
		// Get the value of the selected value from spinner tolerance
		baseDropMenu1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			    @Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			    	Object item = parent.getItemAtPosition(pos);
			    }
			    
			    @Override
				public void onNothingSelected(AdapterView<?> parent) {
			    	// DO NOTHING
			    }
		});
	}

	private void addItemsOnSpinner2() {
		List<String> list = new ArrayList<String>();
		list.add("Ω");
		list.add("kΩ");
		list.add("MΩ");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		baseDropMenu2.setAdapter(dataAdapter);
		
		// Get the value of the selected value from spinner tolerance
		baseDropMenu2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			    @Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			    	Object item = parent.getItemAtPosition(pos);
			    }
			    
			    @Override
				public void onNothingSelected(AdapterView<?> parent) {
			    	// DO NOTHING
			    }
			    
		});
	}

	private double getResistance1Value() {
		if (resistance1.getText().length() == 0)
			return 0;
		else
			return Double.parseDouble(resistance1.getText().toString());
	}
	
	private double getResistance2Value() {
		if (resistance2.getText().length() == 0)
			return 0;
		else
			return Double.parseDouble(resistance2.getText().toString());
	}
	
	private char baseIntToChar1() {
		int baseIntValue = baseDropMenu1.getSelectedItemPosition();
		if (baseIntValue == 0)
			return 'o';
		else if (baseIntValue == 1)
			return 'k';
		else
			return 'M';
	}
	
	private char baseIntToChar2() {
		int baseIntValue = baseDropMenu2.getSelectedItemPosition();
		if (baseIntValue == 0)
			return 'o';
		else if (baseIntValue == 1)
			return 'k';
		else
			return 'M';
	}
}
