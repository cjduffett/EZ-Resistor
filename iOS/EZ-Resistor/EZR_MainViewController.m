//
//  EZR_MainViewController.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 12/29/13.
//  Copyright (c) 2013 Stack Underflow. All rights reserved.
//

#import "EZR_MainViewController.h"
#import "EZR_InterfaceColors.h"
#import <QuartzCore/QuartzCore.h>

@interface EZR_MainViewController ()

// buttons
@property (weak, nonatomic) IBOutlet UIButton *menu_button_r2c;
@property (weak, nonatomic) IBOutlet UIButton *menu_button_c2r;
@property (weak, nonatomic) IBOutlet UIButton *menu_button_eqr;

@end

@implementation EZR_MainViewController

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
    
    // adjust navigation bar appearance
    self.navigationController.navigationBar.barStyle = UIBarStyleBlackTranslucent;
        
    // FORMAT BUTTONS:
    // --------------------------------------------------------------------
    
    // interface color palate
    EZR_InterfaceColors *interfaceColors = [[EZR_InterfaceColors alloc] initColors];
    
    // Resistance to Color
    self.menu_button_r2c.layer.cornerRadius = 6;
    self.menu_button_r2c.layer.borderWidth = 2;
    self.menu_button_r2c.layer.borderColor = [UIColor whiteColor].CGColor;
    self.menu_button_r2c.backgroundColor = interfaceColors.blue;
    [self.menu_button_r2c.titleLabel setFont: [UIFont fontWithName:@"HelveticaNeue-Bold" size:23]];
    
    // Color to Resistance
    self.menu_button_c2r.layer.cornerRadius = 6;
    self.menu_button_c2r.layer.borderWidth = 2;
    self.menu_button_c2r.layer.borderColor = [UIColor whiteColor].CGColor;
    self.menu_button_c2r.backgroundColor = interfaceColors.red;
    [self.menu_button_c2r.titleLabel setFont: [UIFont fontWithName:@"HelveticaNeue-Bold" size:23]];
    
    // Equivalent Resistance
    self.menu_button_eqr.layer.cornerRadius = 6;
    self.menu_button_eqr.layer.borderWidth = 2;
    self.menu_button_eqr.layer.borderColor = [UIColor whiteColor].CGColor;
    self.menu_button_eqr.backgroundColor = interfaceColors.gray;
    [self.menu_button_eqr.titleLabel setFont: [UIFont fontWithName:@"HelveticaNeue-Bold" size:23]];
    
    // ---------------------------------------------------------------------
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
