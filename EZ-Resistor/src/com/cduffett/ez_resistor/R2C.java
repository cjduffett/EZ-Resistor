package com.cduffett.ez_resistor;


public class R2C {
	
	// private color int array
	private int colors[] = new int[4];
	private String errorMessage;
	
	// Accessors
	public int getFirst()
	{
		return colors[0];
	}
	
	public int getSecond()
	{
		return colors[1];
	}
	
	public int getThird()
	{
		return colors[2];
	}
	
	public int getFourth()
	{
		return colors[3];
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	// function to set the color arrays and check for errors
	public boolean Res2C(double resistance, char multiplier, int tolerance) {
		
		// MO	99.5
		// kO	99500
		// Error Check
		if (resistance < 0.1 || resistance >= 99500000)
		{
			errorMessage = "Invalid resistance! Please enter value between 0.1 and 99500000 Ω.";
			return false;
		}
		else if (multiplier == 'k' && resistance >= 99500)
		{
			errorMessage = "Invalid resistance! Please select a value less than 99500 kΩ.";
			return false;
		}
		else if (multiplier == 'M' && resistance >= 99.5 )
		{
			errorMessage = "Invalid resistance! Please select a value less than 99.5 MΩ.";
			return false;
		}
		else
		{
		//Declaring variables
			int thirdBand = 0;
			
			//Adjust the entered value by the base;
			switch (multiplier){
				case 'o':
					break;
				case 'k':
					resistance = resistance * Math.pow(10, 3);
					break;
				case 'M':
					resistance = resistance * Math.pow(10,6);
					break;
			}
			
			//Finds the first two significant figures (first two band colors) and also the third band value.  If the resistor exists ( .1 <= R <= 99499999 [program
			//rounds 99499999 back down to 99000000, the actual max]), it sets the value of resistance to the significant figures
			for (int i = -2; i <= 6; i++){
				if (resistance/(Math.pow(10,i)) >= 9.95 && resistance/(Math.pow(10,i)) < 99.5){
					resistance = Math.floor((resistance/(Math.pow(10,i)))+.5);
					thirdBand = i;
					break;
				}
			}
			
			//Separates the first and second digits
			int first = (int) (resistance/10);
			int second = (int) (resistance-(10*first));
			
			colors[0] = first;
			colors[1] = second;
			colors[2] = thirdBand;
			colors[3] = tolerance;
			return true;
		}
	}
}
