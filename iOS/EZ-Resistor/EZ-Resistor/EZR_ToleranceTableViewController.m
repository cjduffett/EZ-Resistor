//
//  EZR_ToleranceTableViewController.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/3/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_ToleranceTableViewController.h"

@interface EZR_ToleranceTableViewController ()

@property (nonatomic, strong) NSArray *tolerances;

@end

@implementation EZR_ToleranceTableViewController

@synthesize tolerances;

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
    tolerances = [NSArray arrayWithObjects: @"± 2%", @"± 5%", @"± 10%", @"± 20%", nil];
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
    return [self.tolerances count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"TolerancesCell";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    // Configure the cell...
    cell.textLabel.text = [self.tolerances objectAtIndex: indexPath.row];
    
    return cell;
}

@end
