//
//  EZR_BandColors.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/2/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_BandColors.h"

@implementation EZR_BandColors

@synthesize black, brown, red, orange, yellow, green, blue, violet, gray, white, gold, silver;

-(id) initColors
{
    self = [super init];
    
    if(self)
    {
        // initialize colors for resistor bands using RGB (converted from hex):
        
        black = [UIColor colorWithRed:0/255.0f green:0/255.0f blue:0/255.0f alpha:1.0f];        // 0xff 000000
        brown = [UIColor colorWithRed:115/255.0f green:51/255.0f blue:6/255.0f alpha:1.0f];     // 0xff 733306
        red = [UIColor colorWithRed:201/255.0f green:2/255.0f blue:2/255.0f alpha:1.0f];        // 0xff c90202
        orange = [UIColor colorWithRed:252/255.0f green:113/255.0f blue:9/255.0f alpha:1.0f];   // 0xff fc7109
        yellow = [UIColor colorWithRed:242/255.0f green:226/255.0f blue:2/255.0f alpha:1.0f];   // 0xff f2e202
        green = [UIColor colorWithRed:15/255.0f green:110/255.0f blue:12/255.0f alpha:1.0f];    // 0xff 0f6e0c
        blue = [UIColor colorWithRed:29/255.0f green:35/255.0f blue:219/255.0f alpha:1.0f];     // 0xff 1d23db
        violet = [UIColor colorWithRed:98/255.0f green:27/255.0f blue:188/255.0f alpha:1.0f];   // 0xff 621bbc
        gray = [UIColor colorWithRed:86/255.0f green:85/255.0f blue:87/255.0f alpha:1.0f];      // 0xff 565557
        white = [UIColor colorWithRed:255/255.0f green:255/255.0f blue:255/255.0f alpha:1.0f];  // 0xff ffffff
        gold = [UIColor colorWithRed:250/255.0f green:176/255.0f blue:2/255.0f alpha:1.0f];     // 0xff fab002
        silver = [UIColor colorWithRed:179/255.0f green:179/255.0f blue:181/255.0f alpha:1.0f]; // 0xff b3b3b5
    }
    return self;
}

@end
