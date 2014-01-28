//
//  EZR_C2RViewController.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/2/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "EZR_C2RViewController.h"
#import "EZR_InterfaceColors.h"
#import "EZR_BandColors.h"
#import "EZR_BandImages.h"

@interface EZR_C2RViewController ()

// interface elements
@property (weak, nonatomic) IBOutlet UIButton *select1;
@property (weak, nonatomic) IBOutlet UIButton *select2;
@property (weak, nonatomic) IBOutlet UIButton *select3;
@property (weak, nonatomic) IBOutlet UIButton *select4;
@property (weak, nonatomic) IBOutlet UIButton *calculate;
@property (weak, nonatomic) IBOutlet UIButton *reset;
@property (weak, nonatomic) IBOutlet UILabel *textField;

// resistor band imageViews
@property (weak, nonatomic) IBOutlet UIImageView *band1;
@property (weak, nonatomic) IBOutlet UIImageView *band2;
@property (weak, nonatomic) IBOutlet UIImageView *band3;
@property (weak, nonatomic) IBOutlet UIImageView *band4;

// unwind segues
- (IBAction)unwindFromBand1Table: (UIStoryboardSegue *) unwindSegue;
- (IBAction)unwindFromBand2Table: (UIStoryboardSegue *) unwindSegue;
- (IBAction)unwindFromBand3Table: (UIStoryboardSegue *) unwindSegue;
- (IBAction)unwindFromBand4Table: (UIStoryboardSegue *) unwindSegue;

// reset, calculate button actions
- (IBAction)calculate: (id)sender;
- (IBAction)reset: (id)sender;

// variables
@property (nonatomic, assign) BOOL band1Selected, band2Selected, band3Selected, band4Selected;
@property (nonatomic, assign) int band1Value, band2Value, band3Value, band4Value, tolerance;
@property (nonatomic, assign) double resistance;

// resistor band images
@property (nonatomic, strong) EZR_BandImages *band_images;

// resistor band color palate
@property (nonatomic, strong) EZR_BandColors *bandColors;
@property (nonatomic, strong) NSArray *bandColorsArray;

@end

@implementation EZR_C2RViewController

@synthesize band1Value, band2Value, band3Value, band4Value,
            tolerance, resistance, band_images, bandColors,
            bandColorsArray, band1Selected, band2Selected,
            band3Selected, band4Selected;

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
	
    // INITIALIZIATION:
    // -----------------------------------------------------------------------------
    
    // initialize variables
    band1Value = band2Value = band3Value = band4Value = tolerance = resistance = 0;
    band1Selected = band2Selected = band3Selected = band4Selected = NO;
    
    // initialize UI color palates
    EZR_InterfaceColors *interfaceColors = [[EZR_InterfaceColors alloc] initColors];
    bandColors = [[EZR_BandColors alloc] initColors];
    
    // bandColorsArray:
    bandColorsArray = [NSArray arrayWithObjects:
                       bandColors.black,
                       bandColors.brown,
                       bandColors.red,
                       bandColors.orange,
                       bandColors.yellow,
                       bandColors.green,
                       bandColors.blue,
                       bandColors.violet,
                       bandColors.gray,
                       bandColors.white,
                       bandColors.silver,
                       bandColors.gold,
                       nil];
    
    // initialize band images
    band_images = [[EZR_BandImages alloc] initImages];
    
    // VIEW FORMATTING:
    // -----------------------------------------------------------------------------
    
    // color selection buttons
    self.select1.layer.cornerRadius = 10;
    self.select1.layer.borderWidth = 4;
    self.select1.layer.borderColor = [UIColor whiteColor].CGColor;
    self.select1.backgroundColor = bandColors.white;
    
    self.select2.layer.cornerRadius = 10;
    self.select2.layer.borderWidth = 4;
    self.select2.layer.borderColor = [UIColor whiteColor].CGColor;
    self.select2.backgroundColor = bandColors.white;
    
    self.select3.layer.cornerRadius = 10;
    self.select3.layer.borderWidth = 4;
    self.select3.layer.borderColor =[UIColor whiteColor].CGColor;
    self.select3.backgroundColor = bandColors.white;
    
    self.select4.layer.cornerRadius = 10;
    self.select4.layer.borderWidth = 4;
    self.select4.layer.borderColor = [UIColor whiteColor].CGColor;
    self.select4.backgroundColor = bandColors.white;
    
    // calculate & reset buttons
    self.calculate.layer.cornerRadius = 6;
    self.calculate.layer.borderWidth = 2;
    self.calculate.layer.borderColor = [UIColor whiteColor].CGColor;
    self.calculate.backgroundColor = interfaceColors.blue;
    
    self.reset.layer.cornerRadius = 6;
    self.reset.layer.borderWidth = 2;
    self.reset.layer.borderColor = [UIColor whiteColor].CGColor;
    self.reset.backgroundColor = interfaceColors.red;
    
    // result textField
    self.textField.layer.cornerRadius = 6;
    self.textField.layer.borderWidth = 2;
    self.textField.layer.borderColor = [UIColor whiteColor].CGColor;
}

// CALCULATE:
// -----------------------------------------------------------------------------

- (IBAction)calculate: (id)sender
{
    // error check
    if (band1Selected && band2Selected && band3Selected && band4Selected)
    {
        // Perform calculation:
        
        char base = ' ';
        
        // resistance based on first two significant figures
        resistance = (band1Value * 10) + band2Value;
        
        // Adjust for mutliplier band
        switch (band3Value){
                // silver
            case -2:
                resistance /= 100;
                break;
                // gold
            case -1:
                resistance /= 10;
                break;
                // black
            case 0:
                resistance *= 1;
                break;
                // brown
            case 1:
                resistance *= 10;
                break;
                // red
            case 2:
                resistance /= 10;
                base = 'k';
                break;
                // orange
            case 3:
                resistance *= 1;
                base = 'k';
                break;
                // yellow
            case 4:
                resistance *= 10;
                base = 'k';
                break;
                // green
            case 5:
                resistance /= 10;
                base = 'M';
                break;
                // blue
            case 6:
                resistance *= 1;
                base = 'M';
                break;
        }
        
        // display result
        self.textField.textColor = [UIColor blackColor];
        self.textField.text = [NSString stringWithFormat: @"%.1f %cΩ ± %d%%", resistance, base, tolerance];

    }
    else
    {
        self.textField.textColor = [UIColor redColor];
        self.textField.text = @"Please select all bands";
    }
}

// RESET:
// -----------------------------------------------------------------------------

- (IBAction)reset: (id)sender
{
    // default resistor images to black
    self.band1.image = [band_images.band1_images objectAtIndex: 0];
    self.band2.image = [band_images.band2_images objectAtIndex: 0];
    self.band3.image = [band_images.band3_images objectAtIndex: 2]; // band3 colors are offset by +2
    self.band4.image = [band_images.band4_images objectAtIndex: 0];
    
    // default selection buttons to white
    self.select1.backgroundColor = bandColors.white;
    self.select2.backgroundColor = bandColors.white;
    self.select3.backgroundColor = bandColors.white;
    self.select4.backgroundColor = bandColors.white;
    
    // clear result
    [self.textField setText: @""];
    [self.textField setTextColor: [UIColor blackColor]];
    
    // reset selection BOOL
    band1Selected = band2Selected = band3Selected = band4Selected = NO;
}

// UNWIND SEGUES FROM COLOR BAND TABLES:
// -----------------------------------------------------------------------------

// BAND 1 SELECTION:
- (IBAction)unwindFromBand1Table: (UIStoryboardSegue *) unwindSegue
{
    UITableViewController *source = unwindSegue.sourceViewController;
    
    // get selected value from table
    band1Value = (int) ([source.tableView indexPathForSelectedRow].row) + 1;
    
    // set image of resistor
    self.band1.image = [band_images.band1_images objectAtIndex: band1Value];
    
    // set color of button
    self.select1.backgroundColor = [bandColorsArray objectAtIndex: band1Value];
    
    band1Selected = YES;
}

// BAND 2 SELECTION:
- (IBAction)unwindFromBand2Table: (UIStoryboardSegue *) unwindSegue
{
    UITableViewController *source = unwindSegue.sourceViewController;
    
    // get selected value from table
    band2Value = (int) [source.tableView indexPathForSelectedRow].row;
    
    // set image of resistor
    self.band2.image = [band_images.band2_images objectAtIndex: band2Value];
    
    // set color of button
    self.select2.backgroundColor = [bandColorsArray objectAtIndex: band2Value];
    
    band2Selected = YES;
}

// BAND 3 SELECTION:
- (IBAction)unwindFromBand3Table: (UIStoryboardSegue *) unwindSegue
{
    UITableViewController *source = unwindSegue.sourceViewController;
    
    // get selected value from table
    band3Value = (int) [source.tableView indexPathForSelectedRow].row;
    
    // set image of resistor
    self.band3.image = [band_images.band3_images objectAtIndex: band3Value];
    
    // set color of button
    switch (band3Value)
    {
        case 0: // silver
            self.select3.backgroundColor = bandColors.silver;
            break;
        case 1: // gold
            self.select3.backgroundColor = bandColors.gold;
            break;
        default:
            self.select3.backgroundColor = [bandColorsArray objectAtIndex: band3Value - 2];
            break;
    }
    
    // adjust value for calculation
    band3Value -= 2; // powers of 10 between -2 and 6
    
    band3Selected = YES;
}

// BAND 4 SELECTION:
- (IBAction)unwindFromBand4Table: (UIStoryboardSegue *) unwindSegue
{
    UITableViewController *source = unwindSegue.sourceViewController;
    
    // get selected value from table
    band4Value = (int) [source.tableView indexPathForSelectedRow].row;
    
    // set band 4 visible by default
    [self.band4 setHidden: NO];
     
    // set image of resistor and color of button; store value for calculation
    switch (band4Value)
    {
        case 0: // silver
            self.band4.image = [band_images.band4_images objectAtIndex: 3];
            self.select4.backgroundColor = bandColors.silver;
            tolerance = 10;
            break;
            
        case 1: // gold
            self.band4.image = [band_images.band4_images objectAtIndex: 2];
            self.select4.backgroundColor = bandColors.gold;
            tolerance = 5;
            break;
            
        case 2: // red
            self.band4.image = [band_images.band4_images objectAtIndex: 1];
            self.select4.backgroundColor = bandColors.red;
            tolerance = 2;
            break;
            
        case 3: // none
            [self.band4 setHidden: YES]; // band is not visible
            self.select4.backgroundColor = bandColors.white;
            tolerance = 20;
            break;
    }
    
    band4Selected = YES;
}

// -----------------------------------------------------------------------------

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
