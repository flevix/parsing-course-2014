#!/usr/bin/perl
use strict;
use warnings;
 
while (<>) {
    print if /(^[^\s].*[^\s]$|^[^\s]*$)/s;
}
