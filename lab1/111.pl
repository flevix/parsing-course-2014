#!/usr/bin/perl
use strict;
use warnings;
 
while (<>) {
    print if /^(0|11|10(00|1)*01)+$/;
}
