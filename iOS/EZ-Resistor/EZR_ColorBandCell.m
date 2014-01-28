//
//  EZR_ColorBandCell.m
//  EZ-Resistor
//
//  Created by Carlton Duffett on 1/6/14.
//  Copyright (c) 2014 Stack Underflow. All rights reserved.
//

#import "EZR_ColorBandCell.h"

@implementation EZR_ColorBandCell

@synthesize colorThumb, colorLabel;

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self)
    {
        // Initialization code...
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
    
}

@end
