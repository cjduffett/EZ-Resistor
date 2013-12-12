package com.cduffett.ez_resistor;

public class C2R {
	
	//resistance needs to be a double because it can have a decimal point
	private double resistance;
	private int tolerance;
	private char base;
	private String errorMessage;
	
	// Default Constructor
	public C2R()
	{
		this.resistance = 0;
		this.tolerance = 0;
		this.base = 'o';
	}
	
	// Function to reset values of the three values
	// Used for precaution
	public void resetValues()
	{
		this.resistance = 0;
		this.tolerance = 0;
		this.base = 'o';
	}
	
	// Function to get the string value of result
	public String getResult()
	{
		if (base == 'o')
			return (resistance + " Ω " + "+/- " + tolerance + "%");
		else if (base == 'k')
			return (resistance + " kΩ " + "+/- " + tolerance + "%");
		else
			return (resistance + " MΩ " + "+/- " + tolerance + "%");
	}
	
	// Function to get errorMessage
	public String getErrorMessage()
	{
		return errorMessage;
	}

	// Function to calculate the three values resValue, tolerance, and base
	public boolean C2ResCalculate(int band1, int band2, int band3, int tolBand){

		
		/*First sig-fig band
		 * Black is on the wheel, but is never actually used
		 * If Black is on our wheel, this does not have to be changed -
		 * resistance is initially set to zero, which black would do
		 */
		switch (band1)
		{
			// Default Choice
			case 0:
				errorMessage = "Invalid choice for band 1!";
				return false;
			// brown
			case 1:
				resistance = 10;
				break;
			// red
			case 2:
				resistance = 20;
				break;
			// orange
			case 3:
				resistance = 30;
				break;
			// yellow
			case 4:
				resistance = 40;
				break;
			// green
			case 5:
				resistance = 50;
				break;
			// blue
			case 6:
				resistance = 60;
				break;
			// purple
			case 7:
				resistance = 70;
				break;
			// gray
			case 8:
				resistance = 80;
				break;
			// white
			case 9:
				resistance = 90;
				break;
		}
		
		/*Second sig-fig band
		 * I explicitly list Black because it can actually happen here
		 * However, like in the first band, I didn't have to because refers to a 0
		 */
		switch (band2){
			// Default Choice
			case 0:
				errorMessage = "Invalid choice for band 2!";
				return false;
			// black
			case 1:
				resistance += 0;
				break;
			// brown
			case 2:
				resistance += 1;
				break;
			// red
			case 3:
				resistance += 2;
				break;
			// orange
			case 4:
				resistance += 3;
				break;
			// yellow
			case 5:
				resistance += 4;
				break;
			// green
			case 6:
				resistance += 5;
				break;
			// blue
			case 7:
				resistance += 6;
				break;
			// purple
			case 8:
				resistance += 7;
				break;
			// gray
			case 9:
				resistance += 8;
				break;
			// white
			case 10:
				resistance += 9;
				break;
		}
		
		/*"Multiplier" band - multiplies the value formed
		 * by the first two bands by a factor of 10
		 */
		switch (band3){
			// Default Choice
			case 0:
				errorMessage = "Invalid choice for band 3!";
				return false;
			// silver
			case 1:
				resistance /= 100;
				break;
			// gold
			case 2:
				resistance /= 10;
				break;
			// black
			case 3:
				resistance *= 1;
				break;
			// brown
			case 4:
				resistance *= 10;
				break;
			// red
			case 5:
				resistance /= 10;
				base = 'k';
				break;
			// orange
			case 6:
				resistance *= 1;
				base = 'k';
				break;
			// yellow
			case 7:
				resistance *= 10;
				base = 'k';
				break;
			// green
			case 8:
				resistance /= 10;
				base = 'M';
				break;
			// blue
			case 9:
				resistance *= 1;
				base = 'M';
				break;
		}
		
		//Tolerance band
		switch (tolBand){
			// Default Choice
			case 0:
				errorMessage = "Invalid choice for band 4!";
				return false;
			// red
			case 1:
				tolerance = 2;
				break;
			// gold
			case 2:
				tolerance = 5;
				break;
			// silver
			case 3:
				tolerance = 10;
				break;
			// none
			case 4:
				tolerance = 20;
				break;
		}
		
		// Correct inputs
		return true;
	}

}


