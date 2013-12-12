package com.cduffett.ez_resistor;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;

public class R2CActivity extends Activity {
	
	// Entry for user to enter resistance/tolerance
	EditText desiredResistance;
	// Drop-down menu to select base and tolerance
	Spinner baseDropMenu;
	Spinner toleranceDropMenu;
	// Calculate and Reset Button
	Button calculateButton;
	Button resetButton;
	// ImageView widgets
	ImageView colorband1;
	ImageView colorband2;
	ImageView colorband3;
	ImageView colorband4;
	// Drawable objects
	Drawable colorband1Drawable;
	Drawable colorband2Drawable;
	Drawable colorband3Drawable;
	Drawable colorband4Drawable;
	
	R2C R2C_Calculator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activityr2c);
		
		// Assign variables to XML elements
		initializeVariables();
		
		// Initializes spinner objects
		addItemsOnBaseSpinner();
		addItemsOnToleranceSpinner();
		
		
		// When calculateButton is clicked
		calculateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				// Resistance Value (checks for empty)
				double resistanceValue = getResistanceValue();
				
				// Convert the string value from baseDropMenu and assign it to char
				char baseCharValue = baseIntToChar();
				
				// Convert the string value from toleranceDropMenu and assign to int
				int toleranceIntValue = toleranceStringToInt();
				
				if (R2C_Calculator.Res2C(resistanceValue, baseCharValue, toleranceIntValue))
					assignColorValues(R2C_Calculator); 
				else
				{
					Toast.makeText(getApplicationContext(), R2C_Calculator.getErrorMessage(),
							Toast.LENGTH_SHORT).show();
					colorband1Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
					colorband2Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
					colorband3Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
					colorband4Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
					colorband4.setVisibility(View.VISIBLE);
				}
			}

		});
		
		// When resetButton is clicked
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				doReset();
			}
		});
	}

	private void initializeVariables() {
		baseDropMenu = (Spinner) findViewById(R.id.R2C_dropmenu_ohmmagnitude);
		toleranceDropMenu = (Spinner) findViewById(R.id.R2C_dropmenu_desiredtolerance);
		desiredResistance = (EditText) findViewById(R.id.R2C_textfield_desiredresistance);
		calculateButton = (Button) findViewById(R.id.R2C_button_calculate);
		resetButton = (Button) findViewById(R.id.R2C_button_reset);
		
		colorband1 = (ImageView) findViewById(R.id.R2C_image_colorband1);
		colorband2 = (ImageView) findViewById(R.id.R2C_image_colorband2);
		colorband3 = (ImageView) findViewById(R.id.R2C_image_colorband3);
		colorband4 = (ImageView) findViewById(R.id.R2C_image_colorband4);
		
		// Assign Drawables ImageView and set default color to black
		colorband1Drawable = getResources().getDrawable(R.drawable.band1);
		colorband1Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
		colorband1.setImageDrawable(colorband1Drawable);
		colorband2Drawable = getResources().getDrawable(R.drawable.band2);
		colorband2Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
		colorband2.setImageDrawable(colorband2Drawable);
		colorband3Drawable = getResources().getDrawable(R.drawable.band3);
		colorband3Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
		colorband3.setImageDrawable(colorband3Drawable);
		colorband4Drawable = getResources().getDrawable(R.drawable.band4);
		colorband4Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
		colorband4.setImageDrawable(colorband4Drawable);
		
		R2C_Calculator = new R2C();
	}
	
	// Spinner for baseDropMenu
	private void addItemsOnBaseSpinner() {
		List<String> list = new ArrayList<String>();
		list.add("Ω");
		list.add("kΩ");
		list.add("MΩ");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		baseDropMenu.setAdapter(dataAdapter);
		
		/*	**NOT NEEDED BUT CODE KEPT IN CASE**
		// Get the value of the selected value from spinner baseDropMenu
		baseDropMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
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
		*/
	}
	
	// Spinner for toleranceDropMenu
	private void addItemsOnToleranceSpinner() {
		List<String> list = new ArrayList<String>();
		list.add("+/-    2%");
		list.add("+/-    5%");
		list.add("+/-    10%");
		list.add("+/-    20%");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toleranceDropMenu.setAdapter(dataAdapter);
		
		// Get the value of the selected value from spinner tolerance
		toleranceDropMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
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
		
		// Set default tolerance to +/- 5%
		toleranceDropMenu.setSelection(1);
	}

	// Check for empty user input of resistanceValue
	private double getResistanceValue() {
		if (desiredResistance.getText().length() == 0)
			return 0;
		else
			return Double.parseDouble(desiredResistance.getText().toString());
	}
	
	// Convert baseIntValue -> baseCharValue
	private char baseIntToChar() {
		int baseIntValue = baseDropMenu.getSelectedItemPosition();
		if (baseIntValue == 0)
			return 'o';
		else if (baseIntValue == 1)
			return 'k';
		else
			return 'M';
	}
	
	// Convert toleranceStringValue -> toleranceIntValue
	// Default set at 2
	private int toleranceStringToInt() {
		int baseIntValue = toleranceDropMenu.getSelectedItemPosition();
		if (baseIntValue == 0)
			return 2;
		else if (baseIntValue == 1)
			return 5;
		else if (baseIntValue == 2)
			return 10;
		else
			return 20;
	}
	
	private void doReset()
	{
		// Set color bands to black and show visibility of fourth band
		colorband1Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
		colorband2Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
		colorband3Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
		colorband4Drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);
		colorband4.setVisibility(View.VISIBLE);
		
		// Clear the inputs
		baseDropMenu.setSelection(0);
		toleranceDropMenu.setSelection(1);
		desiredResistance.setText("");
	}
	
	// Function will assign the colors to the bands
	private void assignColorValues(R2C R2C_Object) {
		// First Band
		switch (R2C_Object.getFirst()) {
			// Brown
			case 1:
				colorband1Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
				break;
			// Red
			case 2:
				colorband1Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
				break;
			// Orange
			case 3:
				colorband1Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
				break;
			// Yellow
			case 4:
				colorband1Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
				break;
			// Green
			case 5:
				colorband1Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
				break;
			// Blue
			case 6:
				colorband1Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
				break;
			// Violet
			case 7:
				colorband1Drawable.setColorFilter(MyColors.Violet, PorterDuff.Mode.MULTIPLY);
				break;
			// Gray
			case 8:
				colorband1Drawable.setColorFilter(MyColors.Gray, PorterDuff.Mode.MULTIPLY);
				break;
			// White
			case 9:
				colorband1Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
				break;
			default:
				colorband1Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
		}
		
		// Second Band
		switch (R2C_Object.getSecond()) {
			// Black
			case 0:
				colorband2Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
				break;
			// Brown
			case 1:
				colorband2Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
				break;
			// Red
			case 2:
				colorband2Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
				break;
			// Orange
			case 3:
				colorband2Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
				break;
			// Yellow
			case 4:
				colorband2Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
				break;
			// Green
			case 5:
				colorband2Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
				break;
			// Blue
			case 6:
				colorband2Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
				break;
			// Violet
			case 7:
				colorband2Drawable.setColorFilter(MyColors.Violet, PorterDuff.Mode.MULTIPLY);
				break;
			// Gray
			case 8:
				colorband2Drawable.setColorFilter(MyColors.Gray, PorterDuff.Mode.MULTIPLY);
				break;
			// White
			case 9:
				colorband2Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
				break;
			default:
				colorband2Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
		}
		
		// Third Band
		switch (R2C_Object.getThird()) {
			// Silver
			case -2:
				colorband3Drawable.setColorFilter(MyColors.Silver, PorterDuff.Mode.MULTIPLY);
				break;
			// Gold
			case -1:
				colorband3Drawable.setColorFilter(MyColors.Gold, PorterDuff.Mode.MULTIPLY);
				break;
			// Black
			case 0:
				colorband3Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
				break;
			// Brown
			case 1:
				colorband3Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
				break;
			// Red
			case 2:
				colorband3Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
				break;
			// Orange
			case 3:
				colorband3Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
				break;
			// Yellow
			case 4:
				colorband3Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
				break;
			// Green
			case 5:
				colorband3Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
				break;
			// Blue
			case 6:
				colorband3Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
				break;
			default:
				colorband3Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
		}
		
		// Fourth Band
		switch (R2C_Object.getFourth()) {
			// Red
			case 2:
				colorband4.setVisibility(View.VISIBLE);
				colorband4Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
				break;
			// Gold
			case 5:
				colorband4.setVisibility(View.VISIBLE);
				colorband4Drawable.setColorFilter(MyColors.Gold, PorterDuff.Mode.MULTIPLY);
				break;
			// Silver
			case 10:
				colorband4.setVisibility(View.VISIBLE);
				colorband4Drawable.setColorFilter(MyColors.Silver, PorterDuff.Mode.MULTIPLY);
				break;
			// No Color
			case 20:
				colorband4.setVisibility(View.INVISIBLE);
				break;
			default:
				colorband4Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
		}
	}
}
