//
//  EZR_R2CViewController.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/2/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EZR_R2CViewController.h"
#import "EZR_InterfaceColors.h"
#import "EZR_BandImages.h"

@interface EZR_R2CViewController ()

// Inteface buttons
@property (weak, nonatomic) IBOutlet UIButton *r2c_button_calculate;
@property (weak, nonatomic) IBOutlet UIButton *r2c_button_reset;
@property (weak, nonatomic) IBOutlet UIButton *r2c_button_tolerance;
@property (weak, nonatomic) IBOutlet UIButton *r2c_button_units;
@property (weak, nonatomic) IBOutlet UITextField *textField;

// Resistor Bands
@property (nonatomic, retain, readwrite) IBOutlet UIImageView *band1;
@property (nonatomic, retain, readwrite) IBOutlet UIImageView *band2;
@property (nonatomic, retain, readwrite) IBOutlet UIImageView *band3;
@property (nonatomic, retain, readwrite) IBOutlet UIImageView *band4;

// Resistor parameters
@property (nonatomic, assign) char unit;
@property (nonatomic, assign) int tolerance;
@property (nonatomic, assign) double resistance;

// Interface button functions
- (IBAction)calculate:(UIButton *)sender;
- (IBAction)reset:(UIButton *)sender;

// Interface option functions
- (IBAction)unwindToR2CUnits:(UIStoryboardSegue *) unwindSegue;
- (IBAction)unwindtoR2CTolerance:(UIStoryboardSegue *) unwindSegue;

// Interface editText functions
- (IBAction)textFieldTextDidChange:(UITextField *)sender;
- (IBAction)textFieldDidBeginEditing:(UITextField *) textField;
- (IBAction)textFieldDidExitEditing:(UITextField *)textField;
- (void)animateTextField:(UITextField *) textField up: (BOOL) up;

// resistor band images
@property (nonatomic, strong) EZR_BandImages *band_images;

@end

@implementation EZR_R2CViewController

@synthesize unit, tolerance, resistance, band_images;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self)
    {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // INITIALIZATIION:
    // -----------------------------------------------------------------------------
    
    // initialize variables
    unit = 'o';             // default to Ohms
    tolerance = 5;       // default to 5% tolerance
    resistance = 0.0;       // default to 0 Ohms resistance
    
    // initialize UI color palate
    EZR_InterfaceColors *interfaceColors = [[EZR_InterfaceColors alloc] initColors];
    
    // allocate resistor band images
    band_images = [[EZR_BandImages alloc] initImages];
    
    // VIEW FORMATTING:
    // -----------------------------------------------------------------------------
    
    // calculate button
    self.r2c_button_calculate.layer.cornerRadius = 6;
    self.r2c_button_calculate.layer.borderWidth = 2;
    self.r2c_button_calculate.layer.borderColor = [UIColor whiteColor].CGColor;
    self.r2c_button_calculate.backgroundColor = interfaceColors.blue;
    
    // reset button
    self.r2c_button_reset.layer.cornerRadius = 6;
    self.r2c_button_reset.layer.borderWidth = 2;
    self.r2c_button_reset.layer.borderColor = [UIColor whiteColor].CGColor;
    self.r2c_button_reset.backgroundColor = interfaceColors.red;
    
    // tolerance button
    self.r2c_button_tolerance.layer.cornerRadius = 6;
    self.r2c_button_tolerance.layer.borderWidth = 2;
    self.r2c_button_tolerance.layer.borderColor = [UIColor whiteColor].CGColor;
    self.r2c_button_tolerance.backgroundColor = [UIColor whiteColor];
    
    // units button
    self.r2c_button_units.layer.cornerRadius = 6;
    self.r2c_button_units.layer.borderWidth = 2;
    self.r2c_button_units.layer.borderColor = [UIColor whiteColor].CGColor;
    self.r2c_button_units.backgroundColor = [UIColor whiteColor];
    
    // -----------------------------------------------------------------------------
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

// CALCULATE:
// -----------------------------------------------------------------------------

- (IBAction)calculate:(UIButton *)sender
{
    BOOL validParams = YES;
    
    // check that value entered is numeric
    NSNumberFormatter* numberFormatter = [[NSNumberFormatter alloc] init];
    
    NSNumber* number = [numberFormatter numberFromString: self.textField.text];
    
    if (number == nil)
    {
        validParams = NO;
        
        // resistance entered is not numeric
        [self.textField setText: @"Value is not a number"];
        [self.textField setTextColor: [UIColor redColor]];
        return;
    }

    // error check parameters:
    if (resistance < 0.1 || resistance >= 99500000) // for units of Ohms
    {
        validParams = NO;
    }
    else if (unit == 'k' && resistance >= 99500)    // units of kOhms
    {
        validParams = NO;
    }
    else if (unit == 'm' && resistance >= 99.5)     // units of MOhms
    {
        validParams = NO;
    }
    
    if (validParams)
    {
        // calculate the numeric values of each band:
        double testRes;
        testRes = resistance;
        
        // adjust the entered resistance value by the base
        if (unit == 'k')
            testRes *= pow(10,3);
        if (unit == 'm')
            testRes *= pow(10,6);
        
        int band1value = 0, band2value = 0, band3value = 0, band4value = 0;
        
        // find value of third band (based on powers of 10)
        for (int i = -2; i <= 6; i++)
        {
            if (testRes/(pow(10,i)) >= 9.95 && testRes/(pow(10,i)) < 99.5)
            {
                testRes = floor((testRes/(pow(10,i)))+.5);
                band3value = i;
                break;
            }
        }
        
        // find first two significant figures (values of first two bands)
        band1value = (int) (testRes/10);
        band2value = (int) (testRes-(10*band1value));
        
        // adjust band3value to interface with image array (index +2)
        band3value += 2;
        
        // band4 value
        switch (tolerance)
        {
            case 2:
                band4value = 1;
                break;
            case 5:
                band4value = 2;
                break;
            case 10:
                band4value = 3;
                break;
            case 20:
                band4value = 0;
                break;
        }
        
        // set the colors of each band
        self.band1.image = [band_images.band1_images objectAtIndex:band1value];
        self.band2.image = [band_images.band2_images objectAtIndex:band2value];
        self.band3.image = [band_images.band3_images objectAtIndex:band3value];
        
        // restore band4 to visible by default
        [self.band4 setHidden: NO];
        
        if (band4value == 0)
            [self.band4 setHidden: YES]; // ± 20% tolerance has NO BAND
        else
            self.band4.image = [band_images.band4_images objectAtIndex:band4value];
    }
    else
    {
        // resistance entered is out of range
        [self.textField setText: @"Value is out of range"];
        [self.textField setTextColor: [UIColor redColor]];
    }
}

// RESET:
// -----------------------------------------------------------------------------

- (IBAction)reset:(UIButton *)sender
{
    // restore default values
    unit = 'o';             // default to Ohms
    tolerance = 0.05;       // default to 5% tolerance
    resistance = 0.0;       // default to 0 Ohms resistance
    
    // reset interface
    [self.r2c_button_units setTitle:@"Ω" forState:UIControlStateNormal];
    [self.r2c_button_tolerance setTitle:@"± 5%" forState: UIControlStateNormal];
    self.textField.text = @"";
    
    // reset resistor bands to black
    self.band1.image = [band_images.band1_images objectAtIndex: 0];
    self.band2.image = [band_images.band2_images objectAtIndex: 0];
    self.band3.image = [band_images.band3_images objectAtIndex: 2]; // band3 colors are offset by +2
    self.band4.image = [band_images.band4_images objectAtIndex: 0];
    
    // if necessary, restore band4 to visible
    [self.band4 setHidden: NO];
}

// UNWIND SEGUES FROM UNITS AND TOLERANCE TABLES:
// -----------------------------------------------------------------------------

// UNITS SELECTOR:
-(IBAction)unwindToR2CUnits:(UIStoryboardSegue *) unwindSegue
{
    UITableViewController *source = unwindSegue.sourceViewController;
    
    if ([source.tableView indexPathForSelectedRow].row == 0)        // Ohms
    {
        unit = 'o';
        // set text of edit field
        [self.r2c_button_units setTitle:@"Ω" forState:UIControlStateNormal];
    }
    else if ([source.tableView indexPathForSelectedRow].row == 1)   // kOhms
    {
        unit = 'k';
        [self.r2c_button_units setTitle:@"kΩ" forState:UIControlStateNormal];
    }
    else                                                            // MOhms
    {
        unit = 'm';
        [self.r2c_button_units setTitle:@"MΩ" forState:UIControlStateNormal];
    }
}

// TOLERANCES SELECTOR:
-(IBAction)unwindtoR2CTolerance:(UIStoryboardSegue *)unwindSegue
{
    UITableViewController *source = unwindSegue.sourceViewController;
    
    if ([source.tableView indexPathForSelectedRow].row == 0)        // ± 2%
    {
        tolerance = 2;
        // set text of edit field
        [self.r2c_button_tolerance setTitle:@"± 2%" forState: UIControlStateNormal];
    }
    else if ([source.tableView indexPathForSelectedRow].row == 1)   // ± 5%
    {
        tolerance = 5;
        [self.r2c_button_tolerance setTitle:@"± 5%" forState: UIControlStateNormal];
    }
    else if ([source.tableView indexPathForSelectedRow].row == 2)   // ± 10%
    {
        tolerance = 10;
        [self.r2c_button_tolerance setTitle:@"± 10%" forState: UIControlStateNormal];
    }
    else                                                            // ± 20%
    {
        tolerance = 20;
        [self.r2c_button_tolerance setTitle:@"± 20%" forState: UIControlStateNormal];
    }
}

// VIEW ANIMATION FOR TEXTFIELD EDITING:
// -----------------------------------------------------------------------------

- (IBAction)textFieldTextDidChange:(UITextField *)sender
{
    // restore black text color upon editing
    sender.textColor = [UIColor blackColor];
}

- (IBAction)textFieldDidBeginEditing:(UITextField *)textField
{
    // shift view position upward
    [self animateTextField: textField up: YES];
}

- (IBAction)textFieldDidExitEditing:(UITextField *) textField
{
    // restore view position
    [self animateTextField: textField up: NO];
    
    // store resistance value
    self.resistance = textField.text.doubleValue;
}

- (void) animateTextField: (UITextField*) textField up: (BOOL) up
{
    const int movementDistance = 60;
    const float movementDuration = 0.3f;
    
    int movement = (up ? -movementDistance : movementDistance);
    
    [UIView beginAnimations: @"anim" context: nil];
    [UIView setAnimationBeginsFromCurrentState: YES];
    [UIView setAnimationDuration: movementDuration];
    self.view.frame = CGRectOffset(self.view.frame, 0, movement);
    [UIView commitAnimations];
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField
{    
    [textField resignFirstResponder];
    return YES;
}
// -----------------------------------------------------------------------------

@end
