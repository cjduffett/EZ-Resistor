//
//  EZR_R2CUnitsTableViewController.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/3/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_R2CUnitsTableViewController.h"

@interface EZR_R2CUnitsTableViewController ()

@property (nonatomic, strong) NSArray *units;

@end

@implementation EZR_R2CUnitsTableViewController

@synthesize units;

- (id)initWithStyle:(UITableViewStyle)style
{
    self = [super initWithStyle:style];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // format navigation bar
    self.navigationController.navigationBar.barStyle = UIBarStyleBlackTranslucent;

    // Uncomment the following line to preserve selection between presentations.
    self.clearsSelectionOnViewWillAppear = NO;
     
    // initialize array
    units = [NSArray arrayWithObjects: @"Ohms (Ω)", @"kiloOhms (kΩ)", @"megaOhms (MΩ)", nil];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [units count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"UnitsCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    // Configure the cell...
    cell.textLabel.text = [units objectAtIndex:indexPath.row];
    
    return cell;
}

@end
