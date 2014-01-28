//
//  EZR_Color3TableViewController.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/6/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_Color3TableViewController.h"
#import "EZR_BandColors.h"
#import "EZR_ColorBandCell.h"

@interface EZR_Color3TableViewController ()

@property NSArray *colorOptions, *colorOptionColors;

@end

@implementation EZR_Color3TableViewController

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

    // list of colors for band 3
    colorOptions = [NSArray arrayWithObjects:
                    @"Silver",
                    @"Gold",
                    @"Black",
                    @"Brown",
                    @"Red",
                    @"Orange",
                    @"Yellow",
                    @"Green",
                    @"Blue",
                    nil];
    
    // array of UIColors for band 3
    EZR_BandColors *bandColors = [[EZR_BandColors alloc] initColors];
    colorOptionColors = [NSArray arrayWithObjects:
                         bandColors.silver,
                         bandColors.gold,
                         bandColors.black,
                         bandColors.brown,
                         bandColors.red,
                         bandColors.orange,
                         bandColors.yellow,
                         bandColors.green,
                         bandColors.blue,
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
    static NSString *CellIdentifier = @"Band3Cell";
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
