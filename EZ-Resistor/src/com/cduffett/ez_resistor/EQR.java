package com.cduffett.ez_resistor;

public class EQR {

	// Local Variable
	private String result;
	private String errorMessage;
	
	// Get the result
	public String getResult() {
		return result;
	}
	
	// Get the error message
	public String getErrorMessage() {
		return errorMessage;
	}
	
	// Error check method
	public boolean isError(double resistance1, double resistance2, char base1, char base2)
	{
		if (resistance1 <= 0)
		{
			errorMessage = "Please enter a resistance greater than 0 for R1!";
			return false;
		}
		else if(base1 == 'o' && resistance1 >= 99500000)
		{
			errorMessage = "Invalid resistance for R1! Please enter a value less than 99500000 Ω.";
			return false;
		}
		else if (base1 == 'k' && resistance1 >= 99500)
		{
			errorMessage = "Invalid resistance for R1! Please enter a value less than 99500 kΩ.";
			return false;
		}
		else if (base1 == 'M' && resistance1 >= 99.5 )
		{
			errorMessage = "Invalid resistance for R1! Please enter a value less than 99.5 MΩ.";
			return false;
		}
		else if (resistance2 <= 0)
		{
			errorMessage = "Please enter a resistance greater than 0 for R2!";
			return false;
		}
		else if (base2 == 'o' && resistance2 >= 99500000)
		{
			errorMessage = "Invalid resistance for R2! Please enter a value less than 99500000 Ω.";
			return false;
		}
		else if (base2 == 'k' && resistance2 >= 99500)
		{
			errorMessage = "Invalid resistance for R2! Please enter a value less than 99500 kΩ.";
			return false;
		}
		else if (base2 == 'M' && resistance2 >= 99.5 )
		{
			errorMessage = "Invalid resistance for R2! Please enter a value less than 99.5 MΩ.";
			return false;
		}
		// If there are no errors, return true
		else
			return true;
	}

	public boolean parallelCalculate(double resistance1, double resistance2, char base1, char base2) {
		// Error Check
		if (isError(resistance1, resistance2, base1, base2) == false)
			return false;
		// If there are no errors, perform calculations
		else
		{
			// Convert to appropriate base scale
			switch (base1)
			{
			case 'k':
				resistance1 *= 1000;
				break;
			case 'M':
				resistance1 *= 1000000;
				break;
			}
			switch (base2)
			{
			case 'k':
				resistance2 *= 1000;
				break;
			case 'M':
				resistance2 *= 1000000;
				break;
			}
			
			// Print the equivalent scale with appropriate base
			double equivalent;
			equivalent = (resistance1 * resistance2) / (resistance1 + resistance2);
			if (equivalent >= 1000000)
				result = String.format( "%.2f", equivalent/1000000) + " MΩ";
			else if (equivalent >= 1000)
				result = String.format( "%.2f", equivalent/1000) + " kΩ";
			else
				result = String.format( "%.2f", equivalent) + " Ω";
			return true;
		}
	}

	public boolean seriesCalculate(Double resistance1, Double resistance2, char base1, char base2) {
		// Error Check
		if (isError(resistance1, resistance2, base1, base2) == false)
			return false;
		// If there are no errors, perform calculations
		else
		{
			// Convert to appropriate base scale
			switch (base1)
			{
			case 'k':
				resistance1 *= 1000;
				break;
			case 'M':
				resistance1 *= 1000000;
				break;
			}
			switch (base2)
			{
			case 'k':
				resistance2 *= 1000;
				break;
			case 'M':
				resistance2 *= 1000000;
				break;
			}
		
			double sumResistance = resistance1+resistance2;
			if (sumResistance >= 1000000)
				result = String.format( "%.2f", sumResistance/1000000 ) + " mΩ";
			else if (sumResistance >= 1000)
				result = String.format( "%.2f", sumResistance/1000 ) + " kΩ";
			else
				result = String.format( "%.2f", sumResistance ) + " Ω";
			return true;
		}
	}
}



