//
//  EZR_InterfaceColors.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/2/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_InterfaceColors.h"

@implementation EZR_InterfaceColors

@synthesize red, blue, gray;

-(id) initColors
{
    self = [super init];
    
    if (self)
    {
        // Initialize colors for interface buttons
        
        red = [UIColor colorWithRed:121/255.0f green:1/255.0f blue:0/255.0f alpha:1.0f];
        blue = [UIColor colorWithRed:20/255.0f green:77/255.0f blue:150/255.0f alpha:1.0f];
        gray = [UIColor colorWithRed:90/255.0f green:78/255.0f blue:81/255.0f alpha:1.0f];
    }
    return self;
}

@end
