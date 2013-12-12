package com.cduffett.ez_resistor;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class C2RActivity extends Activity {
	
	// TextView & ImageView objects
	TextView result;
	ImageView colorband1;
	ImageView colorband2;
	ImageView colorband3;
	ImageView colorband4;
	
	// Drawable objects
	Drawable colorband1Drawable;
	Drawable colorband2Drawable;
	Drawable colorband3Drawable;
	Drawable colorband4Drawable;
	Drawable button1Drawable;
	Drawable button2Drawable;
	Drawable button3Drawable;
	Drawable button4Drawable;
	
	// Buttons
	Button calculateButton;
	Button resetButton;
	
	// Spinners
	Spinner band1DropMenu;
	Spinner band2DropMenu;
	Spinner band3DropMenu;
	Spinner band4DropMenu;
	
	// C2R object
	C2R C2R_Calculator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activityc2r);

		// Assign variables to XML elements
		initializeVariables();
		
		// Initialize spinner objects
		addItemsOnSpinnerBand1();
		addItemsOnSpinnerBand2();
		addItemsOnSpinnerBand3();
		addItemsOnSpinnerBand4();
		
		// On calculate button the four color strings will be sent to backend
		calculateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// If choices are valid
				if (C2R_Calculator.C2ResCalculate(
						band1DropMenu.getSelectedItemPosition(),
						band2DropMenu.getSelectedItemPosition(), 
						band3DropMenu.getSelectedItemPosition(),
						band4DropMenu.getSelectedItemPosition()))
				{
					result.setText(C2R_Calculator.getResult(), TextView.BufferType.EDITABLE);
				}
				// Invalid choice
				else
				{
					Toast.makeText(getApplicationContext(), C2R_Calculator.getErrorMessage(),
							Toast.LENGTH_SHORT).show();
					C2R_Calculator.resetValues();
				}
			}
		});

		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				doReset();
				C2R_Calculator.resetValues();
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	private void initializeVariables() {

		band1DropMenu = (Spinner) findViewById(R.id.C2R_button_dot1);
		band2DropMenu = (Spinner) findViewById(R.id.C2R_button_dot2);
		band3DropMenu = (Spinner) findViewById(R.id.C2R_button_dot3);
		band4DropMenu = (Spinner) findViewById(R.id.C2R_button_dot4);

		
		result = (TextView) findViewById(R.id.C2R_textview_result);
		colorband1 = (ImageView) findViewById(R.id.C2R_image_colorband1);
		colorband2 = (ImageView) findViewById(R.id.C2R_image_colorband2);
		colorband3 = (ImageView) findViewById(R.id.C2R_image_colorband3);
		colorband4 = (ImageView) findViewById(R.id.C2R_image_colorband4);
		
		// Buttons
		calculateButton = (Button) findViewById(R.id.C2R_button_calculate);
		resetButton = (Button) findViewById(R.id.C2R_button_reset);	

		
		// Assign Drawables to the proper images in res/drawable
		colorband1Drawable = getResources().getDrawable(R.drawable.band1);
		colorband1.setImageDrawable(colorband1Drawable);
		button1Drawable = getResources().getDrawable(R.drawable.band1buttoncolor);
		//band1DropMenu.setBackground(button1Drawable);
		
		colorband2Drawable = getResources().getDrawable(R.drawable.band2);
		colorband2.setImageDrawable(colorband2Drawable);
		button2Drawable = getResources().getDrawable(R.drawable.band2buttoncolor);
		//band2DropMenu.setBackground(button2Drawable);
		
		colorband3Drawable = getResources().getDrawable(R.drawable.band3);
		colorband3.setImageDrawable(colorband3Drawable);
		button3Drawable = getResources().getDrawable(R.drawable.band3buttoncolor);
		//band3DropMenu.setBackground(button3Drawable);
		
		colorband4Drawable = getResources().getDrawable(R.drawable.band4);
		colorband4.setImageDrawable(colorband4Drawable);
		button4Drawable = getResources().getDrawable(R.drawable.band4buttoncolor);
		//band4DropMenu.setBackground(button4Drawable);
		
		// Check for sdk version, and use appropriate code
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
		{
			band1DropMenu.setBackgroundDrawable(button1Drawable);
			band2DropMenu.setBackgroundDrawable(button2Drawable);
			band3DropMenu.setBackgroundDrawable(button3Drawable);
			band4DropMenu.setBackgroundDrawable(button4Drawable);
		}
		else
		{
			band1DropMenu.setBackground(button1Drawable);
			band2DropMenu.setBackground(button2Drawable);
			band3DropMenu.setBackground(button3Drawable);
			band4DropMenu.setBackground(button4Drawable);
		}
		
		// Create a new C2R object
		C2R_Calculator = new C2R();
	}

	// Spinner for band1DropMenu
	private void addItemsOnSpinnerBand1() {
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("brown");
		list.add("red");
		list.add("orange");
		list.add("yellow");
		list.add("green");
		list.add("blue");
		list.add("violet");
		list.add("gray");
		list.add("white");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, 
				R.layout.ghosttext, list) {
			 @Override
			    public View getDropDownView(int position, View convertView, ViewGroup parent)
			    {
			        View v = null;

			        // If this is the initial dummy entry, make it hidden
			        if (position == 0) {
			            TextView tv = new TextView(getContext());
			            tv.setHeight(0);
			            tv.setVisibility(View.GONE);
			            v = tv;
			        }
			        else {
			            // Pass convertView as null to prevent reuse of special case views
			            v = super.getDropDownView(position, null, parent);
			        }

			        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling 
			        parent.setVerticalScrollBarEnabled(false);
			        return v;
			    }
		};
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		band1DropMenu.setAdapter(dataAdapter);
		
		// Get the value of the selected value from spinner tolerance
		band1DropMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			    @Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			    	Object item = parent.getItemAtPosition(pos);
			    	
			    	switch (pos) {
						// Default
			    		case 0:
			    			colorband1Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
			    			button1Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
			    			break;
			    		// Brown
						case 1:
							colorband1Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
							break;
						// Red
						case 2:
							colorband1Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
							break;
						// Orange
						case 3:
							colorband1Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
							break;
						// Yellow
						case 4:
							colorband1Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
							break;
						// Green
						case 5:
							colorband1Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
							break;
						// Blue
						case 6:
							colorband1Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
							break;
						// Violet
						case 7:
							colorband1Drawable.setColorFilter(MyColors.Violet, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.Violet, PorterDuff.Mode.MULTIPLY);
							break;
						// Gray
						case 8:
							colorband1Drawable.setColorFilter(MyColors.Gray, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.Gray, PorterDuff.Mode.MULTIPLY);
							break;
						// White
						case 9:
							colorband1Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
							button1Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
			    	}
			    }
			    
			    @Override
				public void onNothingSelected(AdapterView<?> parent) {
			    	// DO NOTHING
			    }
		});
	}
	
	// Spinner for band2DropMenu
	private void addItemsOnSpinnerBand2() {
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("black");
		list.add("brown");
		list.add("red");
		list.add("orange");
		list.add("yellow");
		list.add("green");
		list.add("blue");
		list.add("violet");
		list.add("gray");
		list.add("white");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, 
				R.layout.ghosttext, list) {
			 @Override
			    public View getDropDownView(int position, View convertView, ViewGroup parent)
			    {
			        View v = null;

			        // If this is the initial dummy entry, make it hidden
			        if (position == 0) {
			            TextView tv = new TextView(getContext());
			            tv.setHeight(0);
			            tv.setVisibility(View.GONE);
			            v = tv;
			        }
			        else {
			            // Pass convertView as null to prevent reuse of special case views
			            v = super.getDropDownView(position, null, parent);
			        }

			        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling 
			        parent.setVerticalScrollBarEnabled(false);
			        return v;
			    }
		};
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		band2DropMenu.setAdapter(dataAdapter);
		
		// Get the value of the selected value from spinner tolerance
		band2DropMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			    @Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			    	Object item = parent.getItemAtPosition(pos);
			    	
			    	switch (pos) {
						// Default
			    		case 0:
			    			colorband2Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
			    			button2Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
			    			break;
			    		// Black
			    		case 1:
			    			colorband2Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
			    			button2Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
			    			break;
			    		// Brown
						case 2:
							colorband2Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
							break;
						// Red
						case 3:
							colorband2Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
							break;
						// Orange
						case 4:
							colorband2Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
							break;
						// Yellow
						case 5:
							colorband2Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
							break;
						// Green
						case 6:
							colorband2Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
							break;
						// Blue
						case 7:
							colorband2Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
							break;
						// Violet
						case 8:
							colorband2Drawable.setColorFilter(MyColors.Violet, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.Violet, PorterDuff.Mode.MULTIPLY);
							break;
						// Gray
						case 9:
							colorband2Drawable.setColorFilter(MyColors.Gray, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.Gray, PorterDuff.Mode.MULTIPLY);
							break;
						// White
						case 10:
							colorband2Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
							button2Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
			    	}
			    }
			    @Override
				public void onNothingSelected(AdapterView<?> parent) {
			    	// DO NOTHING
			    }
		});
	}

	// Spinner for band3DropMenu
	private void addItemsOnSpinnerBand3() {
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("silver");
		list.add("gold");
		list.add("black");
		list.add("brown");
		list.add("red");
		list.add("orange");
		list.add("yellow");
		list.add("green");
		list.add("blue");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, 
				R.layout.ghosttext, list) {
			 @Override
			    public View getDropDownView(int position, View convertView, ViewGroup parent)
			    {
			        View v = null;

			        // If this is the initial dummy entry, make it hidden
			        if (position == 0) {
			            TextView tv = new TextView(getContext());
			            tv.setHeight(0);
			            tv.setVisibility(View.GONE);
			            v = tv;
			        }
			        else {
			            // Pass convertView as null to prevent reuse of special case views
			            v = super.getDropDownView(position, null, parent);
			        }

			        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling 
			        parent.setVerticalScrollBarEnabled(false);
			        return v;
			    }
		};
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		band3DropMenu.setAdapter(dataAdapter);
		
		// Get the value of the selected value from spinner tolerance
		band3DropMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			    @Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			    	Object item = parent.getItemAtPosition(pos);
			    	
			    	switch (pos) {
						// Default
			    		case 0: 
			    			colorband3Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
			    			button3Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
			    			break;
			    		// Silver
				    	case 1:
							colorband3Drawable.setColorFilter(MyColors.Silver, PorterDuff.Mode.MULTIPLY);
							button3Drawable.setColorFilter(MyColors.Silver, PorterDuff.Mode.MULTIPLY);
							break;
						// Gold
						case 2:
							colorband3Drawable.setColorFilter(MyColors.Gold, PorterDuff.Mode.MULTIPLY);
							button3Drawable.setColorFilter(MyColors.Gold, PorterDuff.Mode.MULTIPLY);
							break;
						// Black
			    		case 3: 
			    			colorband3Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
			    			button3Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
			    			break;
						// Brown
						case 4:
							colorband3Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
							button3Drawable.setColorFilter(MyColors.Brown, PorterDuff.Mode.MULTIPLY);
							break;
						// Red
						case 5:
							colorband3Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
							button3Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
							break;
						// Orange
						case 6:
							colorband3Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
							button3Drawable.setColorFilter(MyColors.Orange, PorterDuff.Mode.MULTIPLY);
							break;
						// Yellow
						case 7:
							colorband3Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
							button3Drawable.setColorFilter(MyColors.Yellow, PorterDuff.Mode.MULTIPLY);
							break;
						// Green
						case 8:
							colorband3Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
							button3Drawable.setColorFilter(MyColors.Green, PorterDuff.Mode.MULTIPLY);
							break;
						// Blue
						case 9:
							colorband3Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
							button3Drawable.setColorFilter(MyColors.Blue, PorterDuff.Mode.MULTIPLY);
			    	}
			    }
			    @Override
				public void onNothingSelected(AdapterView<?> parent) {
			    	// DO NOTHING
			    }
		});
	}

	// Spinner for band4DropMenu
	private void addItemsOnSpinnerBand4() {
		List<String> list = new ArrayList<String>();
		list.add("");
		list.add("red");
		list.add("gold");
		list.add("silver");
		list.add("none");
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, 
				R.layout.ghosttext, list) {
			 @Override
			 public View getDropDownView(int position, View convertView, ViewGroup parent)
		     {
		        View v = null;

		        // If this is the initial dummy entry, make it hidden
		        if (position == 0) {
		            TextView tv = new TextView(getContext());
		            tv.setHeight(0);
		            tv.setVisibility(View.GONE);
		            v = tv;
		        }
		        else {
		            // Pass convertView as null to prevent reuse of special case views
		            v = super.getDropDownView(position, null, parent);
		        }

		        // Hide scroll bar because it appears sometimes unnecessarily, this does not prevent scrolling 
		        parent.setVerticalScrollBarEnabled(false);
		        return v;
		    }
		};
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		band4DropMenu.setAdapter(dataAdapter);
		
		// Get the value of the selected value from spinner tolerance
		band4DropMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			    @Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			    	Object item = parent.getItemAtPosition(pos);
			    	
			    	switch (pos) {
						// Default
			    		case 0:
			    			colorband4.setVisibility(View.VISIBLE);
			    			colorband4Drawable.setColorFilter(MyColors.Black, PorterDuff.Mode.MULTIPLY);
			    			button4Drawable.setColorFilter(MyColors.White, PorterDuff.Mode.MULTIPLY);
			    			break;
						// Red
						case 1:
							colorband4.setVisibility(View.VISIBLE);
							colorband4Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
							button4Drawable.setColorFilter(MyColors.Red, PorterDuff.Mode.MULTIPLY);
							break;
						// Gold
						case 2:
							colorband4.setVisibility(View.VISIBLE);
							colorband4Drawable.setColorFilter(MyColors.Gold, PorterDuff.Mode.MULTIPLY);
							button4Drawable.setColorFilter(MyColors.Gold, PorterDuff.Mode.MULTIPLY);
							break;
						// Silver
						case 3:
							colorband4.setVisibility(View.VISIBLE);
							colorband4Drawable.setColorFilter(MyColors.Silver, PorterDuff.Mode.MULTIPLY);
							button4Drawable.setColorFilter(MyColors.Silver, PorterDuff.Mode.MULTIPLY);
							break;
						// No Color
						case 4:
							colorband4.setVisibility(View.INVISIBLE);
							button4Drawable.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
							break;
			    	}
			    }

			    @Override
				public void onNothingSelected(AdapterView<?> parent) {
			    	// DO NOTHING
			    }
		});
	}

	// Resets the color bands
	private void doReset()
	{
		band1DropMenu.setSelection(0);
		band2DropMenu.setSelection(0);
		band3DropMenu.setSelection(0);
		band4DropMenu.setSelection(0);
		result.setText("Result");
	}
}
