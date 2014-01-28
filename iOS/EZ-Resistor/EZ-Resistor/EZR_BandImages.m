//
//  EZR_BandImages.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/4/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_BandImages.h"

@implementation EZR_BandImages

@synthesize band1_images, band2_images, band3_images, band4_images;

-(id) initImages
{
    self = [super init];
    
    if(self)
    {
        // initialize image arrays
        band1_images = [NSArray arrayWithObjects:
                                 [UIImage imageNamed:@"band1_black.png"],
                                 [UIImage imageNamed:@"band1_brown.png"],
                                 [UIImage imageNamed:@"band1_red.png"],
                                 [UIImage imageNamed:@"band1_orange.png"],
                                 [UIImage imageNamed:@"band1_yellow.png"],
                                 [UIImage imageNamed:@"band1_green.png"],
                                 [UIImage imageNamed:@"band1_blue.png"],
                                 [UIImage imageNamed:@"band1_violet.png"],
                                 [UIImage imageNamed:@"band1_grey.png"],
                                 [UIImage imageNamed:@"band1_white.png"],
                                 nil];
        
        band2_images = [NSArray arrayWithObjects:
                                 [UIImage imageNamed:@"band2_black.png"],
                                 [UIImage imageNamed:@"band2_brown.png"],
                                 [UIImage imageNamed:@"band2_red.png"],
                                 [UIImage imageNamed:@"band2_orange.png"],
                                 [UIImage imageNamed:@"band2_yellow.png"],
                                 [UIImage imageNamed:@"band2_green.png"],
                                 [UIImage imageNamed:@"band2_blue.png"],
                                 [UIImage imageNamed:@"band2_violet.png"],
                                 [UIImage imageNamed:@"band2_grey.png"],
                                 [UIImage imageNamed:@"band2_white.png"],
                                 nil];
        
        band3_images = [NSArray arrayWithObjects:
                                 [UIImage imageNamed:@"band3_silver.png"],
                                 [UIImage imageNamed:@"band3_gold.png"],
                                 [UIImage imageNamed:@"band3_black.png"],
                                 [UIImage imageNamed:@"band3_brown.png"],
                                 [UIImage imageNamed:@"band3_red.png"],
                                 [UIImage imageNamed:@"band3_orange.png"],
                                 [UIImage imageNamed:@"band3_yellow.png"],
                                 [UIImage imageNamed:@"band3_green.png"],
                                 [UIImage imageNamed:@"band3_blue.png"],
                                 nil];
        
        band4_images = [NSArray arrayWithObjects:
                                 [UIImage imageNamed:@"band4_black.png"],
                                 [UIImage imageNamed:@"band4_red.png"],
                                 [UIImage imageNamed:@"band4_gold.png"],
                                 [UIImage imageNamed:@"band4_silver.png"],
                                 nil];
    }
    return self;
}

@end
