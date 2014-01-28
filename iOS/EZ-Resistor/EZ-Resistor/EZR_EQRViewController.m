//
//  EZR_EQRViewController.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/2/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_EQRViewController.h"
#import "EZR_InterfaceColors.h"

@interface EZR_EQRViewController ()

// interface elements
@property (weak, nonatomic) IBOutlet UIButton *calculate;
@property (weak, nonatomic) IBOutlet UIButton *reset;
@property (weak, nonatomic) IBOutlet UILabel *textField;
@property (weak, nonatomic) IBOutlet UITextField *res1editText;
@property (weak, nonatomic) IBOutlet UITextField *res2editText;
@property (weak, nonatomic) IBOutlet UIButton *unit1Select;
@property (weak, nonatomic) IBOutlet UIButton *unit2Select;
@property (weak, nonatomic) IBOutlet UIImageView *circuitImage;
@property (weak, nonatomic) IBOutlet UISegmentedControl *circuitSelector;

// unwind segues
- (IBAction) unwindFromUnit1SelectTable: (UIStoryboardSegue *) unwindSegue;
- (IBAction) unwindFromUnit2SelectTable: (UIStoryboardSegue *) unwindSegue;

// segment control
- (IBAction)circuitSelectorDidChange:(UISegmentedControl *)sender;


// variables
@property (nonatomic, assign) double resistance1, resistance2, result;
@property (nonatomic, assign) char unit1, unit2;
@property (nonatomic, assign) BOOL isParallel;

// calculate and reset actions
- (IBAction)calculate:(id)sender;
- (IBAction)reset:(id)sender;


@end

@implementation EZR_EQRViewController

@synthesize resistance1, resistance2, unit1, unit2, isParallel, result;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // INITIALIZATION:
    // -----------------------------------------------------------------------------
    
    // variables
    resistance1 = resistance2 = 0;
    unit1 = unit2 = 'o'; // default to Ohms (Ω)
    isParallel = YES;
    
    // segment control
    self.circuitSelector.selectedSegmentIndex = 0;
    
    // interface color palate
    EZR_InterfaceColors *interfaceColors = [[EZR_InterfaceColors alloc] initColors];
    
    // VIEW FORMATTING:
    // -----------------------------------------------------------------------------
    
    // unit selection buttons
    self.unit1Select.layer.cornerRadius = 6;
    self.unit1Select.layer.borderWidth = 2;
    self.unit1Select.layer.borderColor = [UIColor whiteColor].CGColor;
    
    self.unit2Select.layer.cornerRadius = 6;
    self.unit2Select.layer.borderWidth = 2;
    self.unit2Select.layer.borderColor = [UIColor whiteColor].CGColor;
    
    // textField
    self.textField.layer.cornerRadius = 6;
    self.textField.layer.borderWidth = 2;
    self.textField.layer.borderColor = [UIColor whiteColor].CGColor;
    
    // calculate and reset buttons
    self.calculate.layer.cornerRadius = 6;
    self.calculate.layer.borderWidth = 2;
    self.calculate.layer.borderColor = [UIColor whiteColor].CGColor;
    self.calculate.backgroundColor = interfaceColors.blue;
    
    self.reset.layer.cornerRadius = 6;
    self.reset.layer.borderWidth = 2;
    self.reset.layer.borderColor = [UIColor whiteColor].CGColor;
    self.reset.backgroundColor = interfaceColors.red;
}

// SEGMENTED SELECTION:       (PARALLEL) & (SERIES)
// -----------------------------------------------------------------------------
// Selection defaults to "Parallel" (isParallel == YES)

- (IBAction)circuitSelectorDidChange:(UISegmentedControl *)sender
{
    isParallel = !isParallel;
    
    if (isParallel)
    {
        self.circuitImage.image = [UIImage imageNamed: @"Parallel_circuit.png"];
    }
    else
    {
        self.circuitImage.image = [UIImage imageNamed: @"Series_circuit.png"];
    }
}

// CALCULATE:
// -----------------------------------------------------------------------------

- (IBAction)calculate:(id)sender
{
    // error check
    BOOL validParams = YES;
    
    // check that both values were entered
    if (self.res1editText.text.length == 0 || self.res2editText.text.length == 0)
    {
        validParams = NO;
        
        // one or more resistance values not entered
        [self.textField setText: @"Incomplete parameter(s)"];
        [self.textField setTextColor: [UIColor redColor]];
        return;
    }
    
    // check that values entered are numeric
    NSNumberFormatter *numberFormatter = [[NSNumberFormatter alloc] init];
    
    NSNumber *number1 = [numberFormatter numberFromString: self.res1editText.text];
    NSNumber *number2 = [numberFormatter numberFromString: self.res2editText.text];
    
    if (number1 == nil || number2 == nil)
    {
        validParams = NO;
        
        // resistance entered is not numeric
        [self.textField setText: @"Value(s) not a number"];
        [self.textField setTextColor: [UIColor redColor]];
        return;
    }
    
    // fetch resistance values from the edit fields
    resistance1 = [self.res1editText.text doubleValue];
    resistance2 = [self.res2editText.text doubleValue];
    
    // error check resistance range
    if (resistance1 < 0.1 || resistance1 >= 99500000) // for units of Ohms
    {
        validParams = NO;
    }
    else if (unit1 == 'k' && resistance1 >= 99500)    // units of kOhms
    {
        validParams = NO;
    }
    else if (unit1 == 'm' && resistance1 >= 99.5)     // units of MOhms
    {
        validParams = NO;
    }
    
    if (resistance2 < 0.1 || resistance2 >= 99500000) // for units of Ohms
    {
        validParams = NO;
    }
    else if (unit2 == 'k' && resistance2 >= 99500)    // units of kOhms
    {
        validParams = NO;
    }
    else if (unit2 == 'm' && resistance2 >= 99.5)     // units of MOhms
    {
        validParams = NO;
    }

    if (validParams)
    {
        
        // convert all resistances into Ohms
        switch (unit1)
        {
            case 'k':
                resistance1 *= 1000;
                break;
            case 'm':
                resistance1 *= 1000000;
                break;
        }
        
        switch (unit2)
        {
            case 'k':
                resistance2 *= 1000;
                break;
            case 'm':
                resistance2 *= 1000000;
                break;
        }
        
        if (isParallel) // PARALLEL CIRCUIT CONFIGURATION
        {
            result = (resistance1 * resistance2)/(resistance1 + resistance2);
        }
        else        // SERIES CIRCUIT CONFIGURATION
        {
            result = resistance1 + resistance2;
        }
    
        char base;
        
        // adjust result for proper unit
        if (result >= 1000000)
        {
            result /= 1000000;
            base = 'M';
        }
        else if (result >= 1000)
        {
            result /= 1000;
            base = 'k';
        }
        else
            base = ' ';
        
        // update result textbox
        self.textField.textColor = [UIColor blackColor];
        [self.textField setText: [NSString stringWithFormat: @"%.2f %cΩ", result, base]];
    }
    
    // Invalid Parameters
    else
    {
        // resistance entered is not numeric
        [self.textField setText: @"Invalid value(s) entered"];
        [self.textField setTextColor: [UIColor redColor]];
    }
}

// RESET:
// -----------------------------------------------------------------------------

- (IBAction)reset:(id)sender
{
    // clear editTextboxes
    resistance1 = resistance2 = 0;
    self.res1editText.text = @"";
    self.res2editText.text = @"";
    
    // clear result Label
    self.textField.text = @"";
    self.textField.textColor = [UIColor blackColor];
    
    // Reset units to Ohms (Ω)
    unit1 = unit2 = 'o';
    [self.unit1Select setTitle: @"Ω" forState: UIControlStateNormal];
    [self.unit2Select setTitle: @"Ω" forState: UIControlStateNormal];    
}

// UNIT SELECTION UNWIND SEGUES:
// -----------------------------------------------------------------------------

- (IBAction)unwindFromUnit1SelectTable: (UIStoryboardSegue *) unwindSegue
{
    UITableViewController *source = unwindSegue.sourceViewController;
    
    if ([source.tableView indexPathForSelectedRow].row == 0)        // Ohms
    {
        unit1 = 'o';
        // set text of edit field
        [self.unit1Select setTitle:@"Ω" forState:UIControlStateNormal];
    }
    else if ([source.tableView indexPathForSelectedRow].row == 1)   // kOhms
    {
        unit1 = 'k';
        [self.unit1Select setTitle:@"kΩ" forState:UIControlStateNormal];
    }
    else                                                            // MOhms
    {
        unit1 = 'm';
        [self.unit1Select setTitle:@"MΩ" forState:UIControlStateNormal];
    }
}

- (IBAction)unwindFromUnit2SelectTable: (UIStoryboardSegue *) unwindSegue
{
    UITableViewController *source = unwindSegue.sourceViewController;
    
    if ([source.tableView indexPathForSelectedRow].row == 0)        // Ohms
    {
        unit2 = 'o';
        // set text of edit field
        [self.unit2Select setTitle:@"Ω" forState:UIControlStateNormal];
    }
    else if ([source.tableView indexPathForSelectedRow].row == 1)   // kOhms
    {
        unit2 = 'k';
        [self.unit2Select setTitle:@"kΩ" forState:UIControlStateNormal];
    }
    else                                                            // MOhms
    {
        unit2 = 'm';
        [self.unit2Select setTitle:@"MΩ" forState:UIControlStateNormal];
    }
}

// VIEW ANIMATION FOR TEXTFIELD EDITING:
// -----------------------------------------------------------------------------

- (IBAction)textFieldDidBeginEditing:(UITextField *)textField
{
    // shift view position upward
    [self animateTextField: textField up: YES];
}

- (IBAction)textFieldDidExitEditing:(UITextField *) textField
{
    // restore view position
    [self animateTextField: textField up: NO];
}

- (void) animateTextField: (UITextField*) textField up: (BOOL) up
{
    const int movementDistance = 125;
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


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
