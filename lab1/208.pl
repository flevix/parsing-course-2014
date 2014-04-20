#!usr/bin/perl
use strict;
use warnings;
 
while (<>) {
    s/([1-9]\d*)0([^\d])/$1$2/g;
    print;
}
