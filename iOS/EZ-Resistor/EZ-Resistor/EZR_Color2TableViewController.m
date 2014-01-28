//
//  EZR_Color2TableViewController.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/6/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_Color2TableViewController.h"
#import "EZR_ColorBandCell.h"
#import "EZR_BandColors.h"

@interface EZR_Color2TableViewController ()

@property NSArray *colorOptions, *colorOptionColors;

@end

@implementation EZR_Color2TableViewController

@synthesize colorOptions, colorOptionColors;

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

    // Uncomment the following line to preserve selection between presentations.
    self.clearsSelectionOnViewWillAppear = NO;
    
    // navigation bar style
    self.navigationController.navigationBar.barStyle = UIBarStyleBlackTranslucent;
    
    // list of colors for band 2
    colorOptions = [NSArray arrayWithObjects:
                    @"Black",
                    @"Brown",
                    @"Red",
                    @"Orange",
                    @"Yellow",
                    @"Green",
                    @"Blue",
                    @"Violet",
                    @"Gray",
                    @"White",
                    nil];
    
    // array of UIColors for band 2
    EZR_BandColors *bandColors = [[EZR_BandColors alloc] initColors];
    colorOptionColors = [NSArray arrayWithObjects:
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
                         nil];
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
    return [colorOptions count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Band2Cell";
    EZR_ColorBandCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier forIndexPath:indexPath];
    
    // Configure the cell...
    [cell.colorLabel setText: [colorOptions objectAtIndex: indexPath.row]];
    [cell.colorThumb setBackgroundColor: [colorOptionColors objectAtIndex: indexPath.row]];
    
    // add border to colorThumb object
    cell.colorThumb.layer.cornerRadius = 2;
    cell.colorThumb.layer.borderWidth = 1;
    cell.colorThumb.layer.borderColor = [UIColor blackColor].CGColor;
    
    return cell;
}

@end
